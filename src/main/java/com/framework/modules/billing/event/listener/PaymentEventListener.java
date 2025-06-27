package com.framework.modules.billing.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.framework.modules.billing.event.PlanDeletedEvent;
import com.framework.modules.billing.event.SubscriptionCanceledEvent;
import com.framework.modules.billing.service.PaymentService;

@Component
public class PaymentEventListener {
   private final PaymentService paymentService;

   public PaymentEventListener(PaymentService paymentService) {
      this.paymentService = paymentService;
   }

   @EventListener
   public void onPlanDeleted(PlanDeletedEvent event) {
      paymentService.cancelPaymentsByPlan(event.getPlan().getId());
   }

   @EventListener
   public void onSubscriptionCanceled(SubscriptionCanceledEvent event) {
      paymentService.cancelPaymentsBySubscription(event.getSubscription().getId());
   }
}
