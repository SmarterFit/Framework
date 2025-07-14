package com.framework.framework.billing.handler;


import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

public interface PaymentHandler {
    public PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO);
    public String getPaymentMethodId();
    public String getPaymentMethodName();
}
