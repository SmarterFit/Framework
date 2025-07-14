package com.framework.framework.billing.handler.impl;

import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

public class AnnuityPaymentHandler implements PaymentHandler {

    @Override
    public PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO) {
        return new PaymentProcessorResponseDTO("Payment Processed.", true);
    }

    @Override
    public String getPaymentMethodId() {
        return "ANNUITY";
    }

    @Override
    public String getPaymentMethodName() {
        return "Anuidade (Boleto/Débito)";
    }
}

