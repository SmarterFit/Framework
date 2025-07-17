package com.framework.modules.billing.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.framework.modules.billing.dto.request.payment.CreatePaymentRequestDTO;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.request.payment.SearchPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;
import com.framework.modules.billing.dto.response.payment.PaymentResponseDTO;
import com.framework.modules.billing.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.framework.modules.billing.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin("*")
public class PaymentController {
   private final PaymentService paymentService;

   @Autowired
   public PaymentController(PaymentService paymentService) {
      this.paymentService = paymentService;
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PostMapping
   public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody @Valid CreatePaymentRequestDTO requestDTO) {
      PaymentResponseDTO paymentResponseDTO = paymentService.createPayment(requestDTO);
      return ResponseEntity.status(201).body(paymentResponseDTO);
   }

   /// Acesso: Dono da assinatura ou funcionários
   @GetMapping("/{id}")
   public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable("id") UUID id) {
      return ResponseEntity.ok(paymentService.getPaymentById(id));
   }

   /// Acesso: Funcionários
   @GetMapping
   public ResponseEntity<List<PaymentResponseDTO>> getAll() {
      List<PaymentResponseDTO> payments = paymentService.getAll();
      return ResponseEntity.ok(payments);
   }

   /// Acesso: Dono da assinatura ou funcionários
   @GetMapping("/usuario/{subscriptionOwnerId}")
   public ResponseEntity<List<PaymentWithSubscriptionResponseDTO>> getAllBySubscriptionOwnerId(
         @PathVariable("subscriptionOwnerId") UUID subscriptionOwnerId) {
      List<PaymentWithSubscriptionResponseDTO> payments = paymentService
            .getAllBySubscriptionOwnerId(subscriptionOwnerId);
      return ResponseEntity.ok(payments);
   }

   @GetMapping("/assinatura/{subscriptionId}")
   public ResponseEntity<List<PaymentResponseDTO>> getAllBySubscriptionId(
         @PathVariable("subscriptionId") UUID subscriptionId) {
      List<PaymentResponseDTO> payments = paymentService.getAllBySubscriptionId(subscriptionId);
      return ResponseEntity.ok(payments);
   }

   /// Dono da assinatura ou funcionários
   @GetMapping("/buscar")
   public ResponseEntity<Page<PaymentResponseDTO>> searchPayments(@ModelAttribute SearchPaymentRequestDTO requestDTO,
         Pageable pageable) {
      return ResponseEntity.ok(paymentService.searchPayments(requestDTO, pageable));
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PatchMapping("/{id}/processar")
   public ResponseEntity<PaymentProcessorResponseDTO> processPayment(@PathVariable UUID id,
         @RequestBody @Valid ProcessorPaymentRequestDTO requestDTO) {
      PaymentProcessorResponseDTO paymentProcessorResponseDTO = paymentService.processPayment(id,
            requestDTO);
      return ResponseEntity.ok(paymentProcessorResponseDTO);
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PatchMapping("/{id}/cancelar")
   public ResponseEntity<Void> cancelPayment(@PathVariable UUID id) {
      paymentService.cancelPayment(id);
      return ResponseEntity.noContent().build();
   }
}
