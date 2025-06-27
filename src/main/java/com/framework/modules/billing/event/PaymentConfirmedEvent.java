package com.framework.modules.billing.event;

import org.springframework.context.ApplicationEvent;

import com.framework.modules.billing.entity.Subscription;

import lombok.Getter;

@Getter
public class PaymentConfirmedEvent extends ApplicationEvent {
   private final Subscription subscription;

   public PaymentConfirmedEvent(Subscription subscription) {
      super(subscription);
      this.subscription = subscription;
   }
}
