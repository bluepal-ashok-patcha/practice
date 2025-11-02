package com.example.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service"))
                .route("user-service", r -> r.path("/api/users/**")
                        .uri("lb://auth-service"))
                .route("contact-service", r -> r.path("/api/contacts/**")
                        .uri("lb://auth-service"))
                .route("chat-service", r -> r.path("/api/chat/**", "/ws/**")
                        .uri("lb://chat-service"))
                .build();
    }
}
