package com.framework.framework.billing.validation;

import org.springframework.stereotype.Component;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.framework.billing.entity.PaymentMethod;
import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.framework.billing.registry.PaymentHandlerRegistry;
import com.framework.framework.billing.repository.PaymentMethodRepository;

@Component
public class PaymentMethodValidation {
   private final PaymentMethodRepository paymentMethodRepository;
   private final PaymentHandlerRegistry paymentHandlerRegistry;

   public PaymentMethodValidation(PaymentMethodRepository paymentMethodRepository,
         PaymentHandlerRegistry paymentHandlerRegistry) {
      this.paymentMethodRepository = paymentMethodRepository;
      this.paymentHandlerRegistry = paymentHandlerRegistry;
   }

   public PaymentMethod validatePaymentMethodName(String name) {
      return paymentMethodRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));
   }

   public PaymentHandler validateHandlerIsAvailable(String handlerName) {
      for (PaymentHandler handler : paymentHandlerRegistry.getHandlers()) {
         if (handler.getPaymentMethodName().equals(handlerName)) {
            return handler;
         }
      }

      throw new ResourceNotFoundException("Payment method not found");
   }
}
