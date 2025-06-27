package com.framework.framework.billing;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {
    public List<String> enabledMethods = new ArrayList<>();


    @PostConstruct
    public void printProps() {
        System.out.println("Enabled methods from properties: " + enabledMethods);
    }


}