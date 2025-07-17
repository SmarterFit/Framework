package com.framework.framework.usermetric.registry;

import com.framework.framework.usermetric.handler.MetricHandler;
import com.framework.framework.usermetric.handler.impl.GenericMetricHandler;
import com.framework.framework.usermetric.initializer.MetricTypeInitializer;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class MetricHandlerRegistry {
    private final List<MetricHandler> handlers;
    private final MetricTypeInitializer metricTypeInitializer;
    private final Map<String, MetricHandler> handlerMap = new HashMap<>();
    private MetricHandler genericMetricHandler;

    public MetricHandlerRegistry(List<MetricHandler> handlers, MetricTypeInitializer metricTypeInitializer,
            GenericMetricHandler genericMetricHandler) {
        this.handlers = handlers;
        this.metricTypeInitializer = metricTypeInitializer;
        this.genericMetricHandler = genericMetricHandler;
        metricTypeInitializer.initializeMetricTypes();
    }

    @PostConstruct
    @Transactional
    public void init() {
        for (MetricHandler handler : handlers) {
            if (!(handler instanceof GenericMetricHandler)) {
                metricTypeInitializer.createMetricType(handler);
                handlerMap.put(handler.getSupportedType(), handler);
            }
        }
    }

    public MetricHandler getHandler(String metricType) {
        return handlerMap.getOrDefault(metricType, genericMetricHandler);
    }
}
