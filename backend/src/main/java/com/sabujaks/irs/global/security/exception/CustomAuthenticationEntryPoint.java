package com.sabujaks.irs.global.security.exception;

import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.ExpressionException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.handlerExceptionResolver = resolver;
    }

    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        Exception exception = (Exception) request.getAttribute("exception");
        if(exception instanceof ExpiredJwtException){
            handlerExceptionResolver.resolveException(request, response, null, exception);
        } else {
            handlerExceptionResolver.resolveException(request, response, null, authenticationException);
        }

    }
}