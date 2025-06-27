package com.framework.modules.billing.processor;

import org.springframework.stereotype.Component;

import com.framework.common.enums.PaymentMethod;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

@Component
public class PixPaymentProcessor implements PaymentProcessor {
   @Override
   public PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO) {
      return new PaymentProcessorResponseDTO("Payment processed", true);
   }

   @Override
   public PaymentMethod getPaymentMethod() {
      return PaymentMethod.PIX;
   }
}
