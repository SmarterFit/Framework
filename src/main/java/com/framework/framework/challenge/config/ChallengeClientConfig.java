package com.framework.framework.challenge.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChallengeClientConfig {

    public ChallengeClientConfig() {
    }

    @Bean
    public ChatClient challengeChatClient(ChatClient.Builder builder) {
        return builder.defaultOptions(ChatOptions.builder().temperature(0.2).build()).build();
    }
}
