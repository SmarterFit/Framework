package com.framework.framework.challenge.service;


import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.challenge.validation.ChallengeHandleValidation;
import com.framework.modules.ai.tools.user.ProfileMetricTools;
import com.framework.modules.challenge.dto.response.ChallengeTrailResponseDTO;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.entity.ChallengeTrail;
import com.framework.modules.challenge.service.ChallengeTrailService;
import com.framework.modules.challenge.validation.ChallengeQuestValidation;
import com.framework.modules.challenge.validation.ChallengeTrailValidation;
import com.framework.modules.useraccess.entity.Profile;
import com.framework.modules.useraccess.validation.ProfileValidation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class ChallengeService {


    private final ChallengeTrailService challengeTrailService;
    private final ChallengeTrailValidation challengeTrailValidation;
    private final ChallengeQuestValidation challengeQuestValidation;
    private final ChallengeHandleValidation challengeHandleValidation;
    private final ProfileValidation profileValidation;

    public ChallengeService(ChallengeTrailService challengeTrailService,
                            ChallengeTrailValidation challengeTrailValidation,
                            ChallengeQuestValidation challengeQuestValidation,
                            ChallengeHandleValidation challengeHandleValidation,
                            ProfileValidation profileValidation) {

        this.challengeTrailService = challengeTrailService;
        this.challengeTrailValidation = challengeTrailValidation;
        this.challengeQuestValidation = challengeQuestValidation;
        this.challengeHandleValidation = challengeHandleValidation;
        this.profileValidation = profileValidation;
    }

    public ChallengeTrailResponseDTO generateChallenge(UUID requesterId, UUID challengeQuestId) throws IOException {
        ChallengeQuest challengeQuest = challengeQuestValidation.validateChallengeQuestById(challengeQuestId);
        ChallengeHandler handler = challengeHandleValidation.getHandler(challengeQuest.getChallengeType());
        Profile profile = profileValidation.validateProfileById(requesterId);

        ChallengeTrail trail = handler.processChallengeQuest(challengeQuest, profile.getId());
        trail.setChallengeQuest(challengeQuest);

        if(challengeTrailValidation.existsTrailByQuestId(challengeQuestId)){
            challengeTrailService.deleteChallengeTrailByQuestId(challengeQuestId);
        }
        return challengeTrailService.create(trail);
    }

}
