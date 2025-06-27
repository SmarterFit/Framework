package com.framework.common.exceptions;

public class PaymentMethodNotFound extends RuntimeException {
    public PaymentMethodNotFound(String message) {
        super(message);
    }
}
