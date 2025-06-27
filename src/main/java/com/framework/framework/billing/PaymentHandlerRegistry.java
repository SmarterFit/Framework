package com.framework.framework.billing;

import com.framework.common.exceptions.BusinessException;
import com.framework.framework.billing.entity.PaymentMethod;
import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.framework.billing.repository.PaymentMethodRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class PaymentHandlerRegistry {

    private final Map<String, PaymentHandler> activeHandlers = new HashMap<>();
    private final PaymentMethodRepository methodRepository;
    private final PaymentProperties paymentProperties;
    private final List<PaymentHandler> handlers;

    public PaymentHandlerRegistry(List<PaymentHandler> handlers,
                                  PaymentMethodRepository methodRepository,
                                  PaymentProperties paymentProperties) {
        this.handlers = handlers;
        this.methodRepository = methodRepository;
        this.paymentProperties = paymentProperties;
    }

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println("Enabled methods from properties: " + paymentProperties.getEnabledMethods());
        initializeActiveHandlers();
    }

    private void initializeActiveHandlers() {
        Set<String> enabledSet = new HashSet<>(paymentProperties.getEnabledMethods());

        for (PaymentHandler handler : handlers) {
            String methodName = handler.getPaymentMethodName();

            PaymentMethod method = new PaymentMethod();
            method.setName(methodName);
            method.setEnabled(enabledSet.contains(methodName));
            method.setHandlerClass(handler.getClass().getName());
            methodRepository.save(method);
            activeHandlers.put(methodName, handler);
        }

        deactivateUnavailableMethods();
    }

    public void deactivateUnavailableMethods() {
        List<String> activeMethodNames = handlers.stream()
                .map(PaymentHandler::getPaymentMethodName)
                .toList();

        methodRepository.deactivateMethodsNotIn(activeMethodNames);
    }

    public void disablePaymentMethod(String methodName) {
        PaymentMethod method = methodRepository.findByName(methodName)
                .orElseThrow(() -> new BusinessException("Payment method not available or disabled: " + methodName));

        method.setEnabled(false);
        methodRepository.save(method);
        activeHandlers.remove(methodName);
    }

    public void activePaymentMethod(String methodName) {
        PaymentMethod method = methodRepository.findByName(methodName)
                .orElseThrow(() -> new BusinessException("Payment method not available or disabled: " + methodName));

        method.setEnabled(true);
        methodRepository.save(method);

        handlers.stream()
                .filter(handler -> handler.getPaymentMethodName().equals(methodName))
                .findFirst()
                .ifPresent(handler -> activeHandlers.put(methodName, handler));
    }

    public Optional<PaymentHandler> getHandler(String methodName) {
        return Optional.ofNullable(activeHandlers.get(methodName));
    }

    public List<String> getSupportedMethods() {
        return List.copyOf(activeHandlers.keySet());
    }

    public boolean isMethodEnabled(String methodName) {
        return activeHandlers.containsKey(methodName);
    }
}



