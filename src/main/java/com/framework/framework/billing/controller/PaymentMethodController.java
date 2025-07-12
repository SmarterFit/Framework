package com.framework.framework.billing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.framework.framework.billing.dto.response.PaymentMethodResponseDTO;
import com.framework.framework.billing.service.PaymentMethodService;

@RestController
@CrossOrigin("*")
@RequestMapping("/pagamentos/metodos")
public class PaymentMethodController {
   private final PaymentMethodService paymentMethodService;

   @Autowired
   public PaymentMethodController(PaymentMethodService paymentMethodService) {
      this.paymentMethodService = paymentMethodService;
   }

   @GetMapping
   public ResponseEntity<List<PaymentMethodResponseDTO>> getEnabledPaymentMethods() {
      return ResponseEntity.ok(paymentMethodService.getEnabledPaymentMethods());
   }

   @PatchMapping("/{id}/ativar")
   public ResponseEntity<PaymentMethodResponseDTO> enablePaymentMethod(@PathVariable String id) {
      return ResponseEntity.ok(paymentMethodService.enablePaymentMethod(id));
   }

   @PatchMapping("/{id}/desativar")
   public ResponseEntity<PaymentMethodResponseDTO> disablePaymentMethod(@PathVariable String id) {
      return ResponseEntity.ok(paymentMethodService.disablePaymentMethod(id));
   }
}
