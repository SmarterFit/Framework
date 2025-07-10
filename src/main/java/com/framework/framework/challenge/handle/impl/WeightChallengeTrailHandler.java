package com.framework.framework.challenge.handle.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.framework.challenge.dto.ChallengeAiDTO;
import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.usermetric.entity.WeightMetricRecord;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.entity.ChallengeStep;
import com.framework.modules.challenge.entity.ChallengeTrail;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class WeightChallengeTrailHandler extends ChallengeHandler {

    @Value("classpath:prompts/challenge-system.txt")
    private Resource challengePrompt;

    protected WeightChallengeTrailHandler(@Qualifier("challengeChatClient") ChatClient chatClient,
                                          UserMetricService userMetricService) {
        super(chatClient, userMetricService);
    }

    @Override
    protected AbstractMetricRecord fetchUserMetric(MetricType metricType, UUID userId) {
        return userMetricService.getLastMetric(userId, metricType.getId());
    }

    @Override
    protected String buildPrompt(ChallengeQuest quest, AbstractMetricRecord lastMetric) throws IOException {
        WeightMetricRecord weightMetric = (WeightMetricRecord) lastMetric;

        String prompt = super.loadPrompt(challengePrompt);

        return prompt
                .replace("{title}", quest.getTitle())
                .replace("{description}", quest.getDescription())
                .replace("{weekly_frequency}", String.valueOf(quest.getDaysOfWeek().size()))
                .replace("{metric_type}", quest.getMetricType().getType())
                .replace("{metric_unit}", quest.getMetricType().getUnit())
                .replace("{last_metric_value}", String.valueOf(weightMetric.getWeight()));
    }


    @Override
    protected List<ChallengeAiDTO> getResponseAi(String prompt) {
        try {
            String aiResponse = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(aiResponse, new TypeReference<List<ChallengeAiDTO>>() {});

        } catch (IOException e) {
            throw new RuntimeException("Error: Parser challenge weight", e);
        }
    }

    @Override
    protected ChallengeTrail generateTrail(ChallengeQuest quest, List<ChallengeAiDTO> challengeAiDTOS) {
        ChallengeTrail trail = new ChallengeTrail();
        List<ChallengeDay> challengeDays = new ArrayList<>();

        LocalDate start = quest.getStartDate();
        LocalDate end = quest.getEndDate();

        // [MONDAY, WEDNESDAY, FRIDAY]
        List<DayOfWeek> selectedDays = quest.getDaysOfWeek();

        Map<Integer, ChallengeAiDTO> aiDayMap = challengeAiDTOS.stream()
                .collect(Collectors.toMap(dto -> Integer.parseInt(dto.getDay()), dto -> dto));

        List<LocalDate> scheduledDates = new ArrayList<>();

        // Pega todas as datas entre start e end que são dos dias selecionados
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (selectedDays.contains(date.getDayOfWeek())) {
                scheduledDates.add(date);
            }
        }

        // Para cada data válida, pega o conteúdo da IA correspondente
        for (int i = 0; i < scheduledDates.size(); i++) {
            LocalDate date = scheduledDates.get(i);
            int aiDayKey = i % selectedDays.size() + 1; // pois AI retorna "1", "2", "3"

            ChallengeAiDTO aiDTO = aiDayMap.get(aiDayKey);

            ChallengeDay day = new ChallengeDay();
            day.setDate(date);

            List<ChallengeStep> steps = aiDTO.getTasks().stream().map(taskDto -> {
                ChallengeStep step = new ChallengeStep();
                step.setDescription(taskDto.getDescription());
                return step;
            }).collect(Collectors.toList());

            day.setSteps(steps);
            challengeDays.add(day);
        }

        trail.setDays(challengeDays);
        return trail;
    }

    @Override
    public String getChallengeType() {
        return "CHALLENGE_WEIGHT";
    }
}
