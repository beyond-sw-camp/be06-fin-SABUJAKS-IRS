package com.service.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/api/member/**")
                    .filters(f -> f.filter(jwtFilter.apply(new JwtFilter.Config())))
                    .uri("lb://msa-member-svc"))  // Load Balancer URI
            .route(r -> r.path("/api/verification/**")
                    .filters(f -> f.filter(jwtFilter.apply(new JwtFilter.Config())))
                    .uri("lb://msa-verification-svc"))  // Load Balancer URI
            .route(r -> r.path("/api/resume/**")
                    .filters(f -> f.filter(jwtFilter.apply(new JwtFilter.Config())))
                    .uri("lb://msa-resume-svc"))  // Load Balancer URI
            .route(r -> r.path("/api/announcement/**")
                    .filters(f-> f.filter(jwtFilter.apply(new JwtFilter.Config())))
                    .uri("lb://msa-announcement-svc"))
            .route(r -> r.path("/api/interview/**")
                    .filters(f-> f.filter(jwtFilter.apply(new JwtFilter.Config())))
                    .uri("lb://msa-interview-svc"))
            .build();
    }
}