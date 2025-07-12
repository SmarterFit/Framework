package com.framework.common.config;

import com.framework.common.security.RoleValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RoleValidationInterceptor roleValidationInterceptor;

    public WebConfig(RoleValidationInterceptor roleValidationInterceptor) {
        this.roleValidationInterceptor = roleValidationInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(roleValidationInterceptor)
                .addPathPatterns("/planos/**")
                .addPathPatterns("/turma/**")
                .addPathPatterns("/modalidade/**")
                .addPathPatterns("/funcionarios/**");
    }
}
