package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.example.api.global.feign_client")
@EnableJpaRepositories(basePackages = {
        "com.example.common",
        "com.example.api"
})

@EntityScan(basePackages = {
        "com.example.common",
        "com.example.api"
})

@ComponentScan(basePackages = {
        "com.example.common",
        "com.example.api"
})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
