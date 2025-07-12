package com.framework.modules.billing.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.framework.billing.entity.PaymentMethod;
import com.framework.framework.billing.mapper.PaymentMethodMapper;
import com.framework.modules.billing.dto.request.payment.CreatePaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentResponseDTO;
import com.framework.modules.billing.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.framework.modules.billing.entity.Payment;
import com.framework.modules.billing.entity.Subscription;

public class PaymentMapper {
   private PaymentMapper() {
      // Private constructor to prevent instantiation
   }

   public static Payment toEntity(CreatePaymentRequestDTO dto, Subscription subscription, PaymentMethod paymentMethod) {
      return toEntity(dto, subscription, paymentMethod, new Payment());
   }

   public static Payment toEntity(CreatePaymentRequestDTO dto, Subscription subscription, PaymentMethod paymentMethod,
         Payment payment) {
      if (payment == null) {
         throw new ResourceNotFoundException("Payment not found.");
      }
      if (subscription == null) {
         throw new ResourceNotFoundException("Subscription not found.");
      }

      if (paymentMethod == null) {
         throw new ResourceNotFoundException("Payment method not found.");
      }

      payment = GenericMapper.map(dto, payment);
      payment.setSubscription(subscription);
      payment.setPaymentMethod(paymentMethod);
      payment.setAmount(subscription.getPlan().getPrice());

      return payment;
   }

   public static PaymentResponseDTO toResponse(Payment payment) {
      if (payment == null) {
         throw new ResourceNotFoundException("Payment not found.");
      }

      PaymentResponseDTO response = GenericMapper.map(payment, PaymentResponseDTO.class);
      response = response.toBuilder()
            .method(PaymentMethodMapper.toResponse(payment.getPaymentMethod()))
            .build();

      return response;
   }

   public static PaymentWithSubscriptionResponseDTO toResponseWithSubscription(Payment payment) {
      if (payment == null) {
         throw new ResourceNotFoundException("Payment not found.");
      }

      PaymentWithSubscriptionResponseDTO response = GenericMapper.map(payment,
            PaymentWithSubscriptionResponseDTO.class);
      response = response
            .toBuilder()
            .subscription(SubscriptionMapper.toResponse(payment.getSubscription()))
            .method(PaymentMethodMapper.toResponse(payment.getPaymentMethod()))
            .build();

      return response;
   }
}
