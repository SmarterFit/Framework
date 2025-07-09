//package com.framework.framework.challenge.handle;
//
//import com.framework.modules.challenge.entity.ChallengeQuest;
//
//public abstract class ChallengeTrailHandler {
//
//    public final ChallengeTrail processChallengeQuest(ChallengeQuest quest) {
//        UserMetric metric = fetchUserMetric(quest);
//        String prompt = buildPrompt(quest, metric);
//        ChallengeTrail trail = generateTrail(prompt);
//        return saveTrail(trail);
//    }
//
//    protected abstract UserMetric fetchUserMetric(ChallengeQuest quest);
//
//    protected abstract String buildPrompt(ChallengeQuest quest, UserMetric metric);
//
//    protected abstract ChallengeTrail generateTrail(String prompt);
//
//    protected abstract ChallengeTrail saveTrail(ChallengeTrail trail);
//
//
//
//}