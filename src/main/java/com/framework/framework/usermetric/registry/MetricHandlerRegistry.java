package com.framework.framework.usermetric.registry;

import com.framework.framework.usermetric.handler.MetricHandler;
import com.framework.framework.usermetric.initializer.MetricTypeInitializer;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class MetricHandlerRegistry {

    private final List<MetricHandler> handlers;
    private final Set<String> systemMetricTypes = new HashSet<>();
    private final MetricTypeInitializer metricTypeInitializer;
    private final Map<String, MetricHandler> systemHandlerMap = new HashMap<>();
    private MetricHandler genericMetricHandler;

    private static final String GENERIC_METRIC_TYPE = "GENERIC_TYPE";

    public MetricHandlerRegistry(List<MetricHandler> handlers, MetricTypeInitializer metricTypeInitializer) {
        this.handlers = handlers;
        this.metricTypeInitializer = metricTypeInitializer;
    }

    @PostConstruct
    @Transactional
    public void init() {
        // Carregar os tipos de métrica do sistema
        systemMetricTypes.addAll(metricTypeInitializer.initializeMetricTypes());

        // Mapear os handlers de sistema
        for (MetricHandler handler : handlers) {
            String supportedType = handler.getSupportedType(); // único tipo
            systemHandlerMap.put(supportedType, handler);
        }

        // Localizar o handler genérico
        genericMetricHandler = handlers.stream()
                .filter(h -> h.supports(GENERIC_METRIC_TYPE))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("GenericMetricHandler not found!"));
    }

    public boolean isSystemMetric(String metricType) {
        return systemMetricTypes.contains(metricType);
    }

    public Optional<MetricHandler> getHandler(String metricType) {
        if (isSystemMetric(metricType)) {
            return Optional.of(genericMetricHandler);
        } else {
            return Optional.ofNullable(systemHandlerMap.get(metricType));
        }
    }
}
