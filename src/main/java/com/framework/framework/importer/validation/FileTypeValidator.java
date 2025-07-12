package com.framework.framework.importer.validation;

import com.framework.framework.importer.registry.ImportHandlerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Component
public class FileTypeValidator {

    private final ImportHandlerRegistry importHandlerRegistry;

    public FileTypeValidator(ImportHandlerRegistry importHandlerRegistry) {
        this.importHandlerRegistry = importHandlerRegistry;
    }


    public String validateAndGetImportMethod(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            throw new IllegalArgumentException("File or Content-Type cannot be null");
        }

        String rawContentType = file.getContentType();
        if (rawContentType == null) {
            throw new IllegalArgumentException("File Content-Type cannot be null");
        }
        String contentType = rawContentType.toLowerCase();

        Optional<String> matchedMethod = importHandlerRegistry.getSupportedMethods().stream()
                .filter(methodName -> contentType.contains(methodName.toLowerCase()))
                .findFirst();

        return matchedMethod.orElseThrow(() ->
                new IllegalArgumentException("Unsupported file content type: " + contentType + ". Supported: " + importHandlerRegistry.getSupportedMethods())
        );
    }
}
