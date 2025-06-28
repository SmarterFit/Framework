package com.framework.framework.usermetric.importer;


import com.framework.framework.usermetric.importer.handler.MetricDataImporterHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Component
public class ImportHandlerRegistry {

    private final Map<String, MetricDataImporterHandler> activeImporters = new HashMap<>();
    private final ImportProperties importProperties;
    private final List<MetricDataImporterHandler> handlers;
    private final Set<String> enabledMethods;

    public ImportHandlerRegistry(List<MetricDataImporterHandler> handlers,
                                 ImportProperties importProperties) {
        this.handlers = handlers;
        this.importProperties = importProperties;
        this.enabledMethods = new HashSet<>(importProperties.getEnabledMethods());
    }

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println("Enabled import methods from properties: " + enabledMethods);
        initializeActiveHandlers();
    }

    private void initializeActiveHandlers() {
        for (MetricDataImporterHandler handler : handlers) {
            String methodName = handler.getImportMethodName();

            if (activeImporters.containsKey(methodName)) {
                throw new IllegalStateException("Duplicate import method name detected: " + methodName);
            }

            activeImporters.put(methodName, handler);
        }

        deactivateUnavailableMethods();
    }


    private void deactivateUnavailableMethods() {
        List<String> activeMethodNames = handlers.stream()
                .map(MetricDataImporterHandler::getImportMethodName)
                .toList();

        List<String> allowedMethods = activeMethodNames.stream()
                .filter(enabledMethods::contains)
                .toList();

        activeImporters.keySet().removeIf(method -> !allowedMethods.contains(method));
    }

    public Optional<MetricDataImporterHandler> getHandler(String methodName) {
        return Optional.ofNullable(activeImporters.get(methodName));
    }

    public List<String> getSupportedMethods() {
        return List.copyOf(activeImporters.keySet());
    }

    public boolean isMethodEnabled(String methodName) {
        return activeImporters.containsKey(methodName);
    }
}


