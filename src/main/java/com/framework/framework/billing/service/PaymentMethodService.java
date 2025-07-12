package com.framework.framework.billing.service;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.framework.billing.dto.response.PaymentMethodResponseDTO;
import com.framework.framework.billing.entity.PaymentMethod;
import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.framework.billing.mapper.PaymentMethodMapper;
import com.framework.framework.billing.registry.PaymentHandlerRegistry;
import com.framework.framework.billing.repository.PaymentMethodRepository;
import com.framework.framework.billing.validation.PaymentMethodValidation;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentMethodService {
   private final PaymentMethodRepository paymentMethodRepository;
   private final PaymentHandlerRegistry paymentHandlerRegistry;
   private final PaymentMethodValidation paymentMethodValidation;

   @Autowired
   public PaymentMethodService(PaymentMethodRepository paymentMethodRepository,
         PaymentHandlerRegistry paymentHandlerRegistry, PaymentMethodValidation paymentMethodValidation) {
      this.paymentMethodRepository = paymentMethodRepository;
      this.paymentHandlerRegistry = paymentHandlerRegistry;
      this.paymentMethodValidation = paymentMethodValidation;
   }

   @PostConstruct
   @Transactional
   private void init() {
      for (Entry<String, PaymentHandler> entry : paymentHandlerRegistry.getActiveHandlers().entrySet()) {
         String methodName = entry.getKey();
         PaymentHandler handler = entry.getValue();

         PaymentMethod type = paymentMethodRepository.findByName(methodName).orElseGet(PaymentMethod::new);
         type.setName(methodName);
         type.setEnabled(true);
         type.setHandlerClass(handler.getClass().getName());
         paymentMethodRepository.save(type);
      }

      paymentMethodRepository.deactivateMethodsNotIn(paymentHandlerRegistry.getSupportedMethods());
   }

   @Transactional(readOnly = true)
   public List<PaymentMethodResponseDTO> getEnabledPaymentMethods() {
      return paymentMethodRepository.findAllByEnabledTrue()
            .stream()
            .map(PaymentMethodMapper::toResponse)
            .toList();
   }

   @Transactional
   public PaymentMethodResponseDTO enablePaymentMethod(String methodName) {
      PaymentMethod paymentMethod = paymentMethodValidation.validatePaymentMethodName(methodName);
      PaymentHandler handler = paymentMethodValidation.validateHandlerIsAvailable(paymentMethod.getName());

      paymentHandlerRegistry.enableHandler(methodName, handler);

      paymentMethod.setEnabled(true);
      paymentMethod = paymentMethodRepository.save(paymentMethod);

      return PaymentMethodMapper.toResponse(paymentMethod);
   }

   @Transactional
   public PaymentMethodResponseDTO disablePaymentMethod(String methodName) {
      PaymentMethod paymentMethod = paymentMethodValidation.validatePaymentMethodName(methodName);
      paymentHandlerRegistry.disableHandler(methodName);

      paymentMethod.setEnabled(false);
      paymentMethod = paymentMethodRepository.save(paymentMethod);

      return PaymentMethodMapper.toResponse(paymentMethod);
   }
}
