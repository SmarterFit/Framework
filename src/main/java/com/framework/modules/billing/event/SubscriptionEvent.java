package com.framework.modules.billing.event;

import com.framework.common.enums.SubscriptionTypeEvent;
import com.framework.modules.billing.entity.Subscription;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubscriptionEvent extends ApplicationEvent {
    private final SubscriptionTypeEvent subscriptionTypeEvent;
    private final Subscription subscription;


    public SubscriptionEvent(SubscriptionTypeEvent typeEvent, Subscription subscription) {
        super(subscription);
        this.subscriptionTypeEvent = typeEvent;
        this.subscription = subscription;
    }
}
