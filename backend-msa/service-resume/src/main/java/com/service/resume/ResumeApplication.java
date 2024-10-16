package com.service.resume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.service.resume.communication")
public class ResumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
    }

}
