package com.framework.modules.billing.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.billing.dto.response.subscriptionuser.SubscriptionUserResponseDTO;
import com.framework.modules.billing.entity.SubscriptionUser;
import com.framework.modules.useraccess.mapper.UserMapper;

public class SubscriptionUserMapper {
   private SubscriptionUserMapper() {
      // Private constructor to prevent instantiation
   }

   public static SubscriptionUserResponseDTO toResponse(SubscriptionUser subscriptionUser) {
      if (subscriptionUser == null) {
         throw new ResourceNotFoundException("SubscriptionUser cannot be null");
      }

      return SubscriptionUserResponseDTO.builder()
            .user(UserMapper.toResponse(subscriptionUser.getUser()))
            .subscription(SubscriptionMapper.toResponse(subscriptionUser.getSubscription()))
            .build();
   }
}
