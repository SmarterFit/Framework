package com.framework.modules.metric.validation;

import com.framework.common.exceptions.BusinessException;
import com.framework.framework.usermetric.importer.ImportHandlerRegistry;
import com.framework.framework.usermetric.importer.handler.MetricDataImporterHandler;
import com.framework.framework.usermetric.metric.MetricHandlerRegistry;
import com.framework.framework.usermetric.metric.handler.MetricHandler;
import org.springframework.stereotype.Component;

@Component
public class UserMetricValidation {

    private final ImportHandlerRegistry importHandlerRegistry;
    private final MetricHandlerRegistry metricHandlerRegistry;

    public UserMetricValidation(ImportHandlerRegistry importHandlerRegistry, MetricHandlerRegistry metricHandlerRegistry) {
        this.importHandlerRegistry = importHandlerRegistry;
        this.metricHandlerRegistry = metricHandlerRegistry;
    }


    public void validateImportMethod(String methodName) {
        if (!importHandlerRegistry.isMethodEnabled(methodName.toUpperCase())) {
            throw new BusinessException("Import method " + methodName + " is not enabled.");
        }
    }

    public MetricDataImporterHandler getImporter(String methodName) {
        return importHandlerRegistry.getHandler(methodName.toUpperCase())
                .orElseThrow(() -> new BusinessException("No importer found for method: " + methodName));
    }

    public MetricHandler getMetricHandler(String metricType) {
        return metricHandlerRegistry.getHandler(metricType)
                .orElseThrow(() -> new BusinessException("No handler found for metric type: " + metricType));
    }
}
