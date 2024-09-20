package com.sabujaks.irs.global.security.filter;

import com.sabujaks.irs.domain.auth.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        List<String> viAuthorizationList = new ArrayList<>();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ATOKEN")) {
                    authorization = cookie.getValue();
                }
                if (cookie.getName().startsWith("VITOKEN")) {
                    viAuthorizationList.add(cookie.getValue());
                }
            }
        }
        if (authorization == null) {
            log.info("인증 쿠키 없음");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authorization;
            Long idx = jwtUtil.getIdx(token);
            String email = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);
            Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
            SimpleGrantedAuthority defaultRole = new SimpleGrantedAuthority(role);
            grantedAuthorities.add(defaultRole);
            for (String grantedAuthorityToken : viAuthorizationList) {
                SimpleGrantedAuthority viRole = new SimpleGrantedAuthority(jwtUtil.getGrantedAuthority(grantedAuthorityToken));
                grantedAuthorities.add(viRole);
            }
            CustomUserDetails customUserDetails = null;
            if (Objects.equals(role, "ROLE_SEEKER")) {
                Seeker seeker = Seeker.builder()
                        .idx(idx)
                        .email(email)
                        .role(role)
                        .build();
                customUserDetails = new CustomUserDetails(seeker, grantedAuthorities);
            }
            if (Objects.equals(role, "ROLE_RECRUITER")) {
                Recruiter recruiter = Recruiter.builder()
                        .idx(idx)
                        .email(email)
                        .role(role)
                        .build();
                customUserDetails = new CustomUserDetails(recruiter, grantedAuthorities);
            }
            if (Objects.equals(role, "ROLE_ESTIMATOR")) {
                Estimator estimator = Estimator.builder()
                        .idx(idx)
                        .email(email)
                        .role(role)
                        .build();
                customUserDetails = new CustomUserDetails(estimator, grantedAuthorities);
            }
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("JWT 처리 중 예외 발생: {}", e.getMessage());
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }
}
