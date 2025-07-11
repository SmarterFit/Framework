package com.framework.framework.challenge.utils;

import com.framework.common.enums.ExperienceLevel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ChallengePromptLoader {
    private String getTypePrompt(ExperienceLevel experienceLevel) {
        return switch (experienceLevel) {
            case BEGINNER -> "prompts/challenge-beginner.txt";
            case INTERMEDIATE -> "prompts/challenge-intermediate.txt";
            case ADVANCED -> "prompts/challenge-advanced.txt";
        };
    }

    public String loadPrompt(ExperienceLevel experienceLevel) throws IOException {
        String promptPath = getTypePrompt(experienceLevel);
        Resource resource = new ClassPathResource(promptPath);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
