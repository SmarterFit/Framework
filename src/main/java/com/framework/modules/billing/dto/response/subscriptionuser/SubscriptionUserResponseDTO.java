package com.framework.modules.billing.dto.response.subscriptionuser;

import com.framework.modules.billing.dto.response.subscription.SubscriptionResponseDTO;
import com.framework.modules.useraccess.dto.response.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SubscriptionUserResponseDTO {
      private UserResponseDTO user;
      private SubscriptionResponseDTO subscription;
}
