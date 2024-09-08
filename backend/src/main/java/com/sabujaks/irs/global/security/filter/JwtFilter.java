package com.sabujaks.irs.global.security.filter;

import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
            String authorization = null;
            if(request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("ATOKEN")) {
                        authorization = cookie.getValue();
                    }
                }
            }
            if(authorization == null){
                log.info("인증 쿠키 없음");
                filterChain.doFilter(request, response);
                return;
            }
            try{
                String token = authorization;
                Long idx = jwtUtil.getIdx(token);
                String email = jwtUtil.getUsername(token);
                String role = jwtUtil.getRole(token);
                log.info(idx + " " + email + " " + role);
                CustomUserDetails customUserDetails = new CustomUserDetails(idx, email, role);
                Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e){
                request.setAttribute("exception", e);
            }
            filterChain.doFilter(request, response);
    }
}
