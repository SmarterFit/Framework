package com.framework.modules.billing.processor;

import com.framework.common.enums.PaymentMethod;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

public interface PaymentProcessor {
   PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO);

   PaymentMethod getPaymentMethod();
}
