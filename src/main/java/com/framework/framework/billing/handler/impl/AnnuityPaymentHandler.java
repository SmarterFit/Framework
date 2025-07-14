package com.framework.framework.billing.handler.impl;

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

