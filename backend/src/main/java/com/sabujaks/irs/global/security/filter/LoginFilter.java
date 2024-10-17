package com.sabujaks.irs.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabujaks.irs.domain.auth.model.request.AuthLoginReq;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthLoginReq dto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            dto = objectMapper.readValue(messageBody, AuthLoginReq.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword());
            return authenticationManager.authenticate(authToken);
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails member = (CustomUserDetails)authResult.getPrincipal();
        Long idx = member.getIdx();
        String email = member.getEmail();
        String role = member.getRole();
//        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
//        for (GrantedAuthority authority : authorities){
//            if(Objects.equals(authority.toString(), role)){
//                continue;
//            }
//            Cookie viToken = new Cookie("VITOKEN"+ UUID.randomUUID(), jwtUtil.createToken(authority.getAuthority()));
//            viToken.setHttpOnly(true);
//            viToken.setSecure(true);
//            viToken.setPath("/");
//            viToken.setMaxAge(60 * 60);
//            response.addCookie(viToken);
//        }
        Cookie aToken = new Cookie("ATOKEN", jwtUtil.createToken(idx, email, role));
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
        aToken.setMaxAge(60 * 60);
        response.addCookie(aToken);
    }
}