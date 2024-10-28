package com.example.api.global.security.filter;

import com.example.common.domain.auth.model.entity.RefreshToken;
import com.example.common.domain.auth.repository.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.api.domain.auth.model.request.AuthLoginReq;
import com.example.api.global.security.CustomUserDetails;
import com.example.api.global.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

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

        // 새로운 AT와 RT 발급
        String accessToken = jwtUtil.createToken(idx, email, role);
        String refreshToken = jwtUtil.createRefreshToken(email);

        // Redis에 리프레시 토큰 저장
        RefreshToken refreshTokenEntity = new RefreshToken(email, refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        // AT와 RT를 쿠키로 설정
        Cookie aToken = new Cookie("ATOKEN", accessToken);
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
//        aToken.setMaxAge(60 * 60); // 만료 시간을 1시간으로 설정, accessToken과 동일
        response.addCookie(aToken);

        Cookie rToken = new Cookie("RTOKEN", refreshToken);
        rToken.setHttpOnly(true);
        rToken.setSecure(true);
        rToken.setPath("/");
//        rToken.setMaxAge(60 * 60 * 24 * 5); // 만료 시간을 5일로 설정, refreshToken과 동일
        response.addCookie(rToken);
    }
}