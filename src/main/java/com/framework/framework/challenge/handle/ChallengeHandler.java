package com.framework.framework.challenge.handle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.common.exceptions.ChallengeProcessingException;
import com.framework.framework.challenge.dto.ChallengeAiDTO;
import com.framework.framework.challenge.utils.ChallengeDayCalculator;
import com.framework.framework.challenge.utils.ChallengePromptLoader;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.entity.ChallengeStep;
import com.framework.modules.challenge.entity.ChallengeTrail;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
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

    protected abstract String fetchUserMetric(MetricType metricType, UUID userId, MetricDataDTO metricDataDTO);

    public abstract String getChallengeTypeId();
    public abstract String getChallengeTypeName();

    public final ChallengeTrail processChallengeQuest(ChallengeQuest quest, UUID userId,
            MetricDataDTO metricDataDTO) throws IOException {

        String lastMetric = fetchUserMetric(quest.getMetricType(), userId, metricDataDTO);
        List<LocalDate> maxChallengeDays = challengeDayCalculator.calculateChallengeDays(
                quest.getStartDate(), quest.getEndDate(), quest.getDaysOfWeek());

        String prompt = buildPrompt(quest, lastMetric, maxChallengeDays.size());
        List<ChallengeAiDTO> challengeAiDTOS = getResponseAi(prompt);
        return generateTrail(quest, challengeAiDTOS, maxChallengeDays);
    }

    private String buildPrompt(ChallengeQuest quest, String lastMetric, int maxChallengeDays) throws IOException {
        String prompt = promptLoader.loadPrompt(quest.getExperienceLevel());

        return prompt
                .replace("{title}", quest.getTitle())
                .replace("{description}", quest.getDescription())
                .replace("{weekly_frequency}", String.valueOf(maxChallengeDays))
                .replace("{metric_type}", quest.getMetricType().getType())
                .replace("{metric_unit}", quest.getMetricType().getUnit())
                .replace("{last_metric_value}", lastMetric);
    }

    private List<ChallengeAiDTO> getResponseAi(String prompt) {
        try {
            String aiResponse = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(aiResponse, new TypeReference<List<ChallengeAiDTO>>() {
            });
        } catch (IOException e) {
            throw new ChallengeProcessingException("Error parsing AI response", e);
        }
    }

    private ChallengeTrail generateTrail(ChallengeQuest quest, List<ChallengeAiDTO> challengeAiDTOS,
            List<LocalDate> maxChallengeDays) {
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

}
