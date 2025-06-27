package com.framework.modules.billing.validation;

import java.time.LocalDateTime;
import java.util.UUID;

import com.framework.framework.billing.PaymentHandlerRegistry;
import com.framework.framework.billing.handler.PaymentHandler;
import org.springframework.stereotype.Component;

import com.framework.common.enums.PaymentStatus;
import com.framework.common.exceptions.BusinessException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.billing.entity.Payment;
import com.framework.modules.billing.entity.Subscription;
import com.framework.modules.billing.repository.PaymentRepository;

@Component
public class PaymentValidation {
   private final PaymentRepository paymentRepository;
   private final PaymentHandlerRegistry paymentHandlerRegistry;

   public PaymentValidation(PaymentRepository paymentRepository, PaymentHandlerRegistry paymentHandlerRegistry) {
      this.paymentRepository = paymentRepository;
      this.paymentHandlerRegistry = paymentHandlerRegistry;
   }

   public Payment validatePaymentById(UUID id) {
      return paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
   }

   public void validatePaymentIsPending(Payment payment) {
      if (payment.getStatus() != PaymentStatus.PENDING) {
         throw new BusinessException("Payment does not have a pending status");
      }
   }

   public void validatePaymentNotExpired(Payment payment) {
      if (payment.getExpirationIn().isBefore(LocalDateTime.now())) {
         throw new BusinessException("Payment has expired");
      }
   }

   public void validateNotHasPendingPaymentForSubscription(Subscription subscription) {
      if (paymentRepository.findBySubscriptionIdAndStatus(subscription.getId(), PaymentStatus.PENDING).isPresent()) {
         throw new BusinessException("Subscription already has a pending payment");
      }
   }

   public PaymentHandler getMethodPayment (String methodName) {
      return paymentHandlerRegistry.getHandler(methodName)
              .orElseThrow(() -> new BusinessException("Payment method not available or disabled: " + methodName));
   }
}
