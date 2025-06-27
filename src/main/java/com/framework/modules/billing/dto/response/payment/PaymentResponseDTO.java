package com.framework.modules.billing.dto.response.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import com.framework.common.enums.PaymentStatus;

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
public class PaymentResponseDTO {
   private UUID id;
   private Double amount;
   private LocalDateTime paymentDate;
   private LocalDateTime expirationIn;
   private String method;
   private PaymentStatus status;
}
