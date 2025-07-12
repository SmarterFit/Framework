package com.framework.modules.billing.dto.response.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import com.framework.common.enums.PaymentStatus;
import com.framework.framework.billing.dto.response.PaymentMethodResponseDTO;
import com.framework.modules.billing.dto.response.subscription.SubscriptionResponseDTO;

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
public class PaymentWithSubscriptionResponseDTO {
   private UUID id;
   private SubscriptionResponseDTO subscription;
   private Double amount;
   private LocalDateTime paymentDate;
   private LocalDateTime expirationIn;
   private PaymentMethodResponseDTO method;
   private PaymentStatus status;
}
