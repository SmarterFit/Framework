package com.smarterfit.framework.billing;

import com.smarterfit.framework.billing.entity.PaymentMethod;
import com.smarterfit.framework.billing.handler.PaymentHandler;
import com.smarterfit.framework.billing.repository.PaymentMethodRepository;
import org.springframework.stereotype.Component;

import java.util.*;

// TODO: Adicionar metodo para verificar a existencia do metodo de pagamento.


@Component
public class PaymentHandlerRegistry {

    private final Map<String, PaymentHandler> activeHandlers = new HashMap<>();
    private final PaymentMethodRepository methodRepository;
    private final PaymentProperties paymentProperties;

    public PaymentHandlerRegistry(List<PaymentHandler> handlers,
                                  PaymentMethodRepository methodRepository,
                                  PaymentProperties paymentProperties) {
        this.methodRepository = methodRepository;
        this.paymentProperties = paymentProperties;
        initializeActiveHandlers(handlers);
    }

    private void initializeActiveHandlers(List<PaymentHandler> handlers) {

        Set<String> enabledSet = new HashSet<>(paymentProperties.getEnabledMethods());

        for (PaymentHandler handler : handlers) {
            String methodName = handler.getPaymentMethodName();

            PaymentMethod method = methodRepository.findByName(methodName).orElseGet(PaymentMethod::new);

            method.setName(methodName);
            method.setEnabled(enabledSet.contains(methodName));
            method.setHandlerClass(handler.getClass().getName());

            methodRepository.save(method);

            if(method.isEnabled()){
                activeHandlers.put(methodName, handler);
            }

            // TODO: Desativar metodos não utilizados no banco


        }
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



