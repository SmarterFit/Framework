package com.framework.framework.challenge.handle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.common.exceptions.ChallengeProcessingException;
import com.framework.framework.challenge.dto.ChallengeAiDTO;
import com.framework.framework.challenge.utils.ChallengeDayCalculator;
import com.framework.framework.challenge.utils.ChallengePromptLoader;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.entity.ChallengeStep;
import com.framework.modules.challenge.entity.ChallengeTrail;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public abstract class ChallengeHandler {

    protected final ChatClient chatClient;
    protected final UserMetricService userMetricService;
    protected ChallengePromptLoader promptLoader = new ChallengePromptLoader();
    protected ChallengeDayCalculator challengeDayCalculator = new ChallengeDayCalculator();

    protected ChallengeHandler(@Qualifier("challengeChatClient") ChatClient chatClient,
                               UserMetricService userMetricService) {
        this.chatClient = chatClient;
        this.userMetricService = userMetricService;
    }

    public final ChallengeTrail processChallengeQuest(ChallengeQuest quest, UUID userId) throws IOException {
        AbstractMetricRecord metric = fetchUserMetric(quest.getMetricType(), userId);
        List<LocalDate> maxChallengeDays = challengeDayCalculator.calculateChallengeDays(
                quest.getStartDate(), quest.getEndDate(), quest.getDaysOfWeek().size());

        String prompt = buildPrompt(quest, metric, maxChallengeDays.size());
        List<ChallengeAiDTO> challengeAiDTOS = getResponseAi(prompt);
        return generateTrail(quest, challengeAiDTOS, maxChallengeDays);
    }

    protected abstract AbstractMetricRecord fetchUserMetric(MetricType metricType, UUID userId);

    protected String buildPrompt(ChallengeQuest quest, AbstractMetricRecord lastMetric, int maxChallengeDays) throws IOException {
        String prompt = promptLoader.loadPrompt(quest.getExperienceLevel());

        return prompt
                .replace("{title}", quest.getTitle())
                .replace("{description}", quest.getDescription())
                .replace("{weekly_frequency}", String.valueOf(maxChallengeDays))
                .replace("{metric_type}", quest.getMetricType().getType())
                .replace("{metric_unit}", quest.getMetricType().getUnit())
                .replace("{last_metric_value}", extractMetricValue(lastMetric));
    }

    private List<ChallengeAiDTO> getResponseAi(String prompt) {
        try {
            String aiResponse = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            Path outputPath = Paths.get("ai-response.json");
            Files.writeString(outputPath, aiResponse);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(aiResponse, new TypeReference<List<ChallengeAiDTO>>() {});
        } catch (IOException e) {
            throw new ChallengeProcessingException("Error parsing AI response", e);
        }
    }

    private ChallengeTrail generateTrail(ChallengeQuest quest, List<ChallengeAiDTO> challengeAiDTOS, List<LocalDate> maxChallengeDays) {
        if (maxChallengeDays.size() != challengeAiDTOS.size()) {
            throw new IllegalStateException("Number of challenge days does not match AI response.");
        }

        ChallengeTrail trail = new ChallengeTrail();
        List<ChallengeDay> challengeDays = new ArrayList<>();

        Map<Integer, ChallengeAiDTO> dayMap = challengeAiDTOS.stream()
                .collect(Collectors.toMap(dto -> Integer.parseInt(dto.getDay()), dto -> dto));

        for (int i = 0; i < maxChallengeDays.size(); i++) {
            LocalDate trainingDate = maxChallengeDays.get(i);
            int finalI = i;
            ChallengeAiDTO aiDTO = Optional.ofNullable(dayMap.get(i + 1))
                    .orElseThrow(() -> new IllegalStateException("No AI response for day " + (finalI + 1)));

            ChallengeDay day = new ChallengeDay();
            day.setDate(trainingDate);
            day.setTrail(trail);
            day.setSteps(createStepsFromAiDTO(aiDTO, day));

            challengeDays.add(day);
        }

        trail.setDays(challengeDays);
        return trail;
    }

    private List<ChallengeStep> createStepsFromAiDTO(ChallengeAiDTO aiDTO, ChallengeDay day) {
        return aiDTO.getTasks().stream().map(taskDto -> {
            ChallengeStep step = new ChallengeStep();
            step.setDescription(taskDto.getDescription());
            step.setDay(day);
            return step;
        }).collect(Collectors.toList());
    }

    protected abstract String extractMetricValue(AbstractMetricRecord lastMetric);

    public abstract String getChallengeType();
}
