package com.framework.modules.billing.service;

import com.framework.common.config.BusinessRules;
import com.framework.common.enums.PaymentStatus;
import com.framework.common.exceptions.BusinessException;
import com.framework.framework.billing.PaymentHandlerRegistry;
import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.event.GamificationEvent;
import com.framework.modules.billing.dto.request.payment.CreatePaymentRequestDTO;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.request.payment.SearchPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;
import com.framework.modules.billing.dto.response.payment.PaymentResponseDTO;
import com.framework.modules.billing.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.framework.modules.billing.entity.Payment;
import com.framework.modules.billing.entity.Subscription;
import com.framework.modules.billing.event.PaymentConfirmedEvent;
import com.framework.modules.billing.mapper.PaymentMapper;
import com.framework.modules.billing.repository.PaymentRepository;
import com.framework.modules.billing.specification.PaymentSpecifications;
import com.framework.modules.billing.validation.PaymentValidation;
import com.framework.modules.billing.validation.SubscriptionValidation;
import com.framework.modules.useraccess.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {
   private final PaymentRepository paymentRepository;
   private final PaymentValidation paymentValidation;
   private final SubscriptionValidation subscriptionValidation;
   private final PaymentHandlerRegistry paymentHandlerRegistry;
   private final ApplicationEventPublisher publisher;

   @Autowired
   public PaymentService(PaymentRepository paymentRepository,
         PaymentValidation paymentValidation,
         SubscriptionValidation subscriptionValidation, ApplicationEventPublisher publisher,
         PaymentHandlerRegistry paymentHandlerRegistry) {
      this.paymentRepository = paymentRepository;
      this.paymentValidation = paymentValidation;
      this.subscriptionValidation = subscriptionValidation;
      this.paymentHandlerRegistry = paymentHandlerRegistry;
      this.publisher = publisher;
   }

   @Transactional
   public PaymentResponseDTO createPayment(CreatePaymentRequestDTO requestDTO) {
      Subscription subscription = subscriptionValidation
            .validateSubscriptionById(requestDTO.getSubscriptionId());

      paymentValidation.validateNotHasPendingPaymentForSubscription(subscription);

      Payment payment = PaymentMapper.toEntity(requestDTO, subscription);
      payment.setExpirationIn(LocalDateTime.now().plusDays(BusinessRules.PAYMENT_EXPIRATION_DAYS));

      paymentRepository.save(payment);

      return PaymentMapper.toResponse(payment);
   }

   @Transactional(readOnly = true)
   public PaymentResponseDTO getPaymentById(UUID id) {
      Payment payment = paymentValidation.validatePaymentById(id);

      return PaymentMapper.toResponse(payment);
   }

   @Transactional(readOnly = true)
   public List<PaymentResponseDTO> getAll() {
      return paymentRepository.findAll().stream().map(PaymentMapper::toResponse).toList();
   }

   @Transactional(readOnly = true)
   public List<PaymentWithSubscriptionResponseDTO> getAllBySubscriptionOwnerId(UUID subscriptionOwnerId) {
      List<Payment> payments = paymentRepository.findBySubscriptionOwnerId(subscriptionOwnerId);

      return payments.stream().map(PaymentMapper::toResponseWithSubscription).toList();
   }

   @Transactional(readOnly = true)
   public List<PaymentResponseDTO> getAllBySubscriptionId(UUID subscriptionId) {
      List<Payment> payments = paymentRepository.findBySubscriptionId(subscriptionId);

      return payments.stream().map(PaymentMapper::toResponse).toList();
   }

   @Transactional(readOnly = true)
   public Page<PaymentResponseDTO> searchPayments(SearchPaymentRequestDTO requestDTO, Pageable pageable) {
      Specification<Payment> specification = PaymentSpecifications.searchByFilters(requestDTO);

      Page<Payment> payments = paymentRepository.findAll(specification, pageable);

      return payments.map(PaymentMapper::toResponse);
   }

   @Transactional
   public PaymentProcessorResponseDTO processPayment(UUID id, ProcessorPaymentRequestDTO requestDTO) {
      Payment payment = paymentValidation.validatePaymentById(id);
      Subscription subscription = payment.getSubscription();

      paymentValidation.validatePaymentIsPending(payment);
      paymentValidation.validatePaymentNotExpired(payment);
      subscriptionValidation.validateSubscriptionNotIsCanceled(subscription);

      PaymentHandler paymentHandler = paymentValidation.getMethodPayment(payment.getMethod());
      System.out.println("Pagamento realizado via : " + paymentHandler.getPaymentMethodName());
      PaymentProcessorResponseDTO response = paymentHandler.processPayment(requestDTO);

      if (response.getSuccess()) {
         payment.setStatus(PaymentStatus.PAID);
         payment.setPaymentDate(LocalDateTime.now());
         paymentRepository.save(payment);

         User user = payment.getSubscription().getOwner();

         GamificationEventRequestDTO dto = GamificationEventRequestDTO.builder()
               .eventType("login")
               .userId(user.getId())
               .details(Map.of("paymentSuccess", Boolean.TRUE))
               .build();
         GamificationEvent event = new GamificationEvent(dto);
         publisher.publishEvent(event);

         publisher.publishEvent(new PaymentConfirmedEvent(subscription));

         return response;
      } else {
         payment.setStatus(PaymentStatus.FAILED);
         paymentRepository.save(payment);
         throw new BusinessException("Payment failed: " + response.getMessage());
      }
   }

   @Transactional
   public void cancelPayment(UUID id) {
      Payment payment = paymentValidation.validatePaymentById(id);

      paymentValidation.validatePaymentIsPending(payment);

      payment.setStatus(PaymentStatus.CANCELED);
      paymentRepository.save(payment);
   }

   @Transactional
   public void expirePaymentsIfNeeded() {
      List<Payment> payments = paymentRepository.findByStatusAndExpirationInBefore(PaymentStatus.PENDING,
            LocalDateTime.now());

      payments.forEach(payment -> payment.setStatus(PaymentStatus.EXPIRED));

      paymentRepository.saveAll(payments);
   }

   @Transactional
   public void cancelPaymentsBySubscription(UUID subscriptionId) {
      paymentRepository.updateStatusBySubscriptionId(subscriptionId, PaymentStatus.CANCELED, PaymentStatus.PENDING);
   }

   @Transactional
   public void cancelPaymentsByPlan(UUID planId) {
      paymentRepository.updateStatusByPlanId(planId, PaymentStatus.CANCELED, PaymentStatus.PENDING);
   }

   @Transactional
   public void disablePaymentMethod(String methodName) {
      paymentHandlerRegistry.disablePaymentMethod(methodName);
   }

   @Transactional
   public void activePaymentMethod(String methodName) {
      paymentHandlerRegistry.activePaymentMethod(methodName);
   }

}
