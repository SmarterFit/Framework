package com.framework.framework.challenge.handle;

import com.framework.framework.challenge.dto.ChallengeAiDTO;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.entity.ChallengeTrail;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Component
public abstract class ChallengeHandler {

    protected final ChatClient chatClient;
    protected final UserMetricService userMetricService;

    protected String loadPrompt(Resource resource) throws IOException {
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }


    protected ChallengeHandler(@Qualifier("challengeChatClient") ChatClient chatClient,
                               UserMetricService userMetricService) {
        this.chatClient = chatClient;
        this.userMetricService = userMetricService;
    }

    public final ChallengeTrail processChallengeQuest(ChallengeQuest quest, UUID userId) throws IOException {
        AbstractMetricRecord metric = fetchUserMetric(quest.getMetricType(), userId);
        String prompt = buildPrompt(quest, metric);
        List<ChallengeAiDTO> challengeAiDTOS = getResponseAi(prompt);
        return generateTrail(quest, challengeAiDTOS);
    }

    protected abstract  AbstractMetricRecord fetchUserMetric(MetricType metricType, UUID userId);

    protected abstract String buildPrompt(ChallengeQuest quest,  AbstractMetricRecord metrics) throws IOException;

    protected abstract List<ChallengeAiDTO> getResponseAi(String prompt);

    protected abstract ChallengeTrail generateTrail(ChallengeQuest quest, List<ChallengeAiDTO> challengeAiDTOS);

    public abstract String getChallengeType();



}