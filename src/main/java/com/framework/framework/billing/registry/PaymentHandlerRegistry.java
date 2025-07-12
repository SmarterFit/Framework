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
            String methodName = handler.getPaymentMethodName();

            if (!enabledSet.contains(methodName)) {
                continue;
            } else if (activeHandlers.containsKey(methodName)) {
                throw new IllegalStateException("Duplicate payment method name detected: " + methodName);
            }

            activeHandlers.put(methodName, handler);
        }
    }

    public Optional<PaymentHandler> getHandler(String methodName) {
        return Optional.ofNullable(activeHandlers.get(methodName));
    }

    public List<String> getSupportedMethods() {
        return List.copyOf(activeHandlers.keySet());
    }

    public Map<String, PaymentHandler> getActiveHandlers() {
        return activeHandlers;
    }

    public boolean isMethodEnabled(String methodName) {
        return activeHandlers.containsKey(methodName);
    }

    public List<PaymentHandler> getHandlers() {
        return handlers;
    }

    public void enableHandler(String methodName, PaymentHandler handler) {
        enabledSet.add(methodName);
        activeHandlers.put(methodName, handler);
    }

    public void disableHandler(String methodName) {
        enabledSet.remove(methodName);
        activeHandlers.remove(methodName);
    }
}
