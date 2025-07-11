package com.framework.framework.challenge.handle.impl;

import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.usermetric.entity.WeightMetricRecord;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class WeightChallengeTrailHandler extends ChallengeHandler {

    private static final String DEFAULT_WEIGHT_VALUE = "0.0";

    protected WeightChallengeTrailHandler(@Qualifier("challengeChatClient") ChatClient chatClient,
                                          UserMetricService userMetricService) {
        super(chatClient, userMetricService);
    }

    @Override
    protected String fetchUserMetric(MetricType metricType, UUID userId, MetricDataDTO metricDataDTO) {
        AbstractMetricRecord lastMetric =  userMetricService.getLastMetric(userId, metricType.getId()).orElse(null);

        return Optional.ofNullable(lastMetric)
                .filter(metric -> metric instanceof WeightMetricRecord)
                .map(metric -> (WeightMetricRecord) metric)
                .map(WeightMetricRecord::getWeight)
                .map(String::valueOf)
                .orElse(DEFAULT_WEIGHT_VALUE);
    }

    @Override
    public String getChallengeType() {
        return "CHALLENGE_WEIGHT";
    }
}
