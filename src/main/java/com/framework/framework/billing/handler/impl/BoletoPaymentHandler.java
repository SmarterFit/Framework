package com.framework.framework.billing.handler.impl;

import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

public class BoletoPaymentHandler implements PaymentHandler {

   @Override
   public PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO) {
      return new PaymentProcessorResponseDTO("Payment processed", true);
   }

   @Override
   public String getPaymentMethodName() {
      return "BOLETO";
   }
}