package com.service.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {
    private final JwtUtil jwtUtil;

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // AtomicReference를 사용해 람다식 안에서 값을 변경 가능하도록 처리
            AtomicReference<String> accessAuthorization = new AtomicReference<>(null);

            if (exchange.getRequest().getCookies() != null) {
                exchange.getRequest().getCookies().forEach((name, cookies) -> {
                    cookies.forEach(cookie -> {
                        if ("ATOKEN".equals(cookie.getName())) {
                            accessAuthorization.set(cookie.getValue());
                        }
                    });
                });
            }

            // ATOKEN이 없으면 필터 체인 실행
            if (accessAuthorization.get() == null) {
                return chain.filter(exchange);
            }

            String token = accessAuthorization.get();
            if (jwtUtil.isExpired(token)) {
                System.out.println("토큰 만료됨");
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            exchange.getRequest().mutate()
                    .header("X-User-Idx", "" + jwtUtil.getIdx(token))
                    .header("X-User-Email", jwtUtil.getUsername(token))
                    .header("X-User-Role", jwtUtil.getRole(token))
                    .build();

            return chain.filter(exchange);
        });
    }
}
