package com.framework.modules.billing.event.listener;

import com.framework.common.enums.SubscriptionTypeEvent;
import com.framework.modules.billing.event.SubscriptionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.framework.modules.billing.event.PlanDeletedEvent;
import com.framework.modules.billing.event.PaymentConfirmedEvent;
import com.framework.modules.billing.service.SubscriptionService;

@Component
public class SubscriptionEventListener {
   private final SubscriptionService subscriptionService;

   public SubscriptionEventListener(SubscriptionService subscriptionService) {
      this.subscriptionService = subscriptionService;
   }

   @EventListener
   public void onPaymentConfirmed(PaymentConfirmedEvent event) {
      subscriptionService.renewSubscription(event.getSubscription());
   }

   @EventListener
   public void onPlanDeleted(PlanDeletedEvent event) {
      subscriptionService.cancelSubscriptionsByPlan(event.getPlan().getId());
   }

   @EventListener
   public void handleGenericEvent(SubscriptionEvent event) {
      if(event.getSubscriptionTypeEvent() == SubscriptionTypeEvent.DECREMENT_AVAILABLE_CLASSES) {
         subscriptionService.decrementAvailableClasses(event.getSubscription());
      }
      else if(event.getSubscriptionTypeEvent() == SubscriptionTypeEvent.INCREMENT_AVAILABLE_CLASSES) {
         subscriptionService.incrementAvailableClasses(event.getSubscription());
      }
   }

}
