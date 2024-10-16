package com.service.verification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.service.verification.communication")
public class VerificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerificationApplication.class, args);
    }

}
