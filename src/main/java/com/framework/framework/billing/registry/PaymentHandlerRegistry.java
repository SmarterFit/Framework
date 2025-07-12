package com.framework.framework.billing.registry;

import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.framework.billing.repository.PaymentMethodRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class PaymentHandlerRegistry {
    private final Map<String, PaymentHandler> activeHandlers = new HashMap<>();
    private final Set<String> enabledSet;
    private final List<PaymentHandler> handlers;

    public PaymentHandlerRegistry(List<PaymentHandler> handlers,
            PaymentMethodRepository methodRepository,
            PaymentProperties paymentProperties) {
        this.handlers = handlers;
        this.enabledSet = new HashSet<>(paymentProperties.getEnabledMethods());
    }

    @PostConstruct
    @Transactional
    public void init() {
        initializeActiveHandlers();
    }

    private void initializeActiveHandlers() {
        for (PaymentHandler handler : handlers) {
            String methodId = handler.getPaymentMethodId();

            if (!enabledSet.contains(methodId)) {
                continue;
            } else if (activeHandlers.containsKey(methodId)) {
                throw new IllegalStateException("Duplicate payment method id detected: " + methodId);
            }

            activeHandlers.put(methodId, handler);
        }
    }

    public Optional<PaymentHandler> getHandler(String methodId) {
        return Optional.ofNullable(activeHandlers.get(methodId));
    }

    public List<String> getSupportedMethods() {
        return List.copyOf(activeHandlers.keySet());
    }

    public Map<String, PaymentHandler> getActiveHandlers() {
        return activeHandlers;
    }

    public boolean isMethodEnabled(String methodId) {
        return activeHandlers.containsKey(methodId);
    }

    public List<PaymentHandler> getHandlers() {
        return handlers;
    }

    public void enableHandler(String methodId, PaymentHandler handler) {
        enabledSet.add(methodId);
        activeHandlers.put(methodId, handler);
    }

    public void disableHandler(String methodId) {
        enabledSet.remove(methodId);
        activeHandlers.remove(methodId);
    }
}
