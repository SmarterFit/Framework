package com.framework.framework.billing.handler.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

@Component
public class CorporatePaymentHandler implements PaymentHandler {

    @Override
    public PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO) {
        return new PaymentProcessorResponseDTO("Payment processed.", true);
    }

    @Override
    public String getPaymentMethodId() {
        return "CORPORATE";
    }

    @Override
    public String getPaymentMethodName() {
        return "Pagamento Corporativo";
    }
}
