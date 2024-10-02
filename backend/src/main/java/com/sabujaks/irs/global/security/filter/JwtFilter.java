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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessAuthorization = null;
        List<String> vidioInterviewAuthorizationList = new ArrayList<>();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ATOKEN")) {
                    accessAuthorization = cookie.getValue();
                }
                if (cookie.getName().startsWith("VITOKEN")) {
                    vidioInterviewAuthorizationList.add(cookie.getValue());
                }
            }
        }
        if (accessAuthorization == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = accessAuthorization;
            Long idx = jwtUtil.getIdx(token);
            String email = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            SimpleGrantedAuthority defaultRole = new SimpleGrantedAuthority(role);
            authorities.add(defaultRole);
            for (String videoInterviewAuthorization : vidioInterviewAuthorizationList) {
                SimpleGrantedAuthority videoInterviewRole = new SimpleGrantedAuthority(jwtUtil.getGrantedAuthority(videoInterviewAuthorization));
                authorities.add(videoInterviewRole);
            }
            CustomUserDetails customUserDetails = null;
            if (Objects.equals(role, "ROLE_SEEKER")) {
                Seeker seeker = Seeker.builder()
                        .idx(idx)
                        .email(email)
                        .role(role)
                        .build();
                customUserDetails = new CustomUserDetails(seeker, authorities);
            }
            if (Objects.equals(role, "ROLE_RECRUITER")) {
                Recruiter recruiter = Recruiter.builder()
                        .idx(idx)
                        .email(email)
                        .role(role)
                        .build();
                customUserDetails = new CustomUserDetails(recruiter, authorities);
            }
            if (Objects.equals(role, "ROLE_ESTIMATOR")) {
                Estimator estimator = Estimator.builder()
                        .idx(idx)
                        .email(email)
                        .role(role)
                        .build();
                customUserDetails = new CustomUserDetails(estimator, authorities);
            }
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | UsernameNotFoundException e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }
}
