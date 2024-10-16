package com.service.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatusCode());
        } else {
            response.setStatusCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String errorCode = Objects.requireNonNull(response.getStatusCode()).toString().split(" ")[0];
        BaseResponse<String> baseResponse;
        if ("404".equals(errorCode)) {
            baseResponse = new BaseResponse<>(BaseResponseMessage.NOT_FOUND, ex.getMessage());
        } else{
            baseResponse = new BaseResponse<>(BaseResponseMessage.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        String jsonResponse = "";
        try {
            jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(baseResponse);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: " + e.getMessage());
            jsonResponse = "{\"message\": \"Error processing the exception\"}";
        }
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
