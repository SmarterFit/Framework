package com.framework.framework.usermetric.metric;

import com.framework.framework.usermetric.metric.handler.MetricHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MetricHandlerRegistry {

    private final List<MetricHandler> handlers;

    public MetricHandlerRegistry(List<MetricHandler> handlers) {
        this.handlers = handlers;
    }

    public Optional<MetricHandler> getHandler(String metricType) {
        return handlers.stream()
                .filter(h -> h.supports(metricType))
                .findFirst();
    }
}
