package com.framework.modules.ai.config;

import com.framework.modules.ai.tools.billing.PlanTools;
import com.framework.modules.ai.tools.classes.ClassPlansTools;
import com.framework.modules.ai.tools.classes.ClassSessionTools;
import com.framework.modules.ai.tools.classes.ClassTools;
import com.framework.modules.ai.tools.classes.UserClassTools;
import com.framework.modules.ai.tools.user.ProfileTools;
import com.framework.modules.ai.tools.user.UserTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Configuration
public class ChatClientConfig {

   @Value("classpath:prompts/smarterfit-gym-system.txt")
   private Resource gymPrompt;

   private final MessageChatMemoryAdvisor memoryAdvisor;
   private final PlanTools planTools;
   private final ClassTools classTools;
   private final UserClassTools userClassTools;
   private final ClassSessionTools classSessionTools;
   private final ClassPlansTools classPlansTools;
   private final UserTools userTools;
   private final ProfileTools profileTools;

   @Autowired
   public ChatClientConfig(PlanTools planTools, ClassTools classTools, UserClassTools userClassTools,
         ClassSessionTools classSessionTools, ClassPlansTools classPlansTools, UserTools userTools,
         ProfileTools profileTools,
         MessageChatMemoryAdvisor memoryAdvisor) {
      this.planTools = planTools;
      this.classTools = classTools;
      this.userClassTools = userClassTools;
      this.classPlansTools = classPlansTools;
      this.classSessionTools = classSessionTools;
      this.userTools = userTools;
      this.profileTools = profileTools;
      this.memoryAdvisor = memoryAdvisor;
   }

   private String loadPrompt(Resource resource) throws IOException {
      return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
   }

   @Bean
   @Primary
   public ChatClient gymChatClient(ChatClient.Builder builder) throws IOException {
      String conversationId = UUID.randomUUID().toString();

      return builder
            .defaultSystem(loadPrompt(gymPrompt))
            .defaultAdvisors(advisor -> advisor
                  .param("conversationId", conversationId)
                  .param("memoryAdvisor", memoryAdvisor))
            .defaultTools(planTools, classTools, userClassTools,
                  classSessionTools, classPlansTools, userTools, profileTools)
            .build();
   }

}
