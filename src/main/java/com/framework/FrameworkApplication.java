package com.framework;

import com.framework.framework.billing.PaymentProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableConfigurationProperties(PaymentProperties.class)
public class FrameworkApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(FrameworkApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("SmarterFit API is running");
		System.out.println("Acesse: http://localhost:8081/");
	}
}
