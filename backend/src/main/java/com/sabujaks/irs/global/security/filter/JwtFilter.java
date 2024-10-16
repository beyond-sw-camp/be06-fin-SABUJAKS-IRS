package com.sabujaks.irs.global.security.filter;

import com.sabujaks.irs.domain.auth.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.RefreshToken;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.RefreshTokenRepository;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.security.CustomUserDetailService;
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = null;
        String refreshToken = null;
        List<String> vidioInterviewAuthorizationList = new ArrayList<>();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ATOKEN")) {
                    accessToken = cookie.getValue();
                }
                if (cookie.getName().equals("RTOKEN")) {
                    refreshToken = cookie.getValue();
                }
                if (cookie.getName().startsWith("VITOKEN")) {
                    vidioInterviewAuthorizationList.add(cookie.getValue());
                }
            }
        }

        // 처음 로그인할 때
        if (accessToken == null && refreshToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Access Token 만료 여부 확인
        if (jwtUtil.isExpired(accessToken)) {

            // Refresh Token이 존재하고 유효한 경우(저장된 Refresh Token과 일치하면) Access Token과 Refresh Token 재발급
            if (refreshToken == null || jwtUtil.isExpired(refreshToken) || !validateRefreshToken(refreshToken)) {
                filterChain.doFilter(request, response);
                return;
            }
            String email = jwtUtil.getEmail(refreshToken);

            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(email);

            // 새로운 Access Token과 Refresh Token 생성
            String newAccessToken = jwtUtil.createToken(userDetails.getIdx(), userDetails.getEmail(), userDetails.getRole());
            String newRefreshToken = jwtUtil.createRefreshToken(userDetails.getEmail());

            // Redis에 저장된 Refresh Token 갱신
            RefreshToken updatedRefreshToken = new RefreshToken(email, newRefreshToken);
            refreshTokenRepository.save(updatedRefreshToken);

            setTokenCookie(response, "ATOKEN", newAccessToken);
            setTokenCookie(response, "RTOKEN", newRefreshToken);

            // 새로운 Access Token을 기반으로 인증 정보 설정
            Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } else {
            // Access Token이 유효한 경우
            try {
                Long idx = jwtUtil.getIdx(accessToken);
                String email = jwtUtil.getUsername(accessToken);
                String role = jwtUtil.getRole(accessToken);
                Set<SimpleGrantedAuthority> authorities = new HashSet<>();
                SimpleGrantedAuthority defaultRole = new SimpleGrantedAuthority(role);
                authorities.add(defaultRole);
                for (String videoInterviewAuthorization : vidioInterviewAuthorizationList) {
                    SimpleGrantedAuthority videoInterviewRole = new SimpleGrantedAuthority(jwtUtil.getGrantedAuthority(videoInterviewAuthorization));
                    authorities.add(videoInterviewRole);
                }

                CustomUserDetails customUserDetails = createCustomUserDetails(role, idx, email, authorities);

                Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | UsernameNotFoundException e) {
                request.setAttribute("exception", e);
            }
        }

        filterChain.doFilter(request, response);
    }

    // CustomUserDetails 생성, 분리했음
    private CustomUserDetails createCustomUserDetails(String role, Long idx, String email, Set<SimpleGrantedAuthority> authorities) {
        if (Objects.equals(role, "ROLE_SEEKER")) {
            Seeker seeker = Seeker.builder()
                    .idx(idx)
                    .email(email)
                    .role(role)
                    .build();
            return new CustomUserDetails(seeker, authorities);
        }
        if (Objects.equals(role, "ROLE_RECRUITER")) {
            Recruiter recruiter = Recruiter.builder()
                    .idx(idx)
                    .email(email)
                    .role(role)
                    .build();
            return new CustomUserDetails(recruiter, authorities);
        }
        if (Objects.equals(role, "ROLE_ESTIMATOR")) {
            Estimator estimator = Estimator.builder()
                    .idx(idx)
                    .email(email)
                    .role(role)
                    .build();
            return new CustomUserDetails(estimator, authorities);
        }
        return null;
    }

    // Refresh Token 추출
    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("RTOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Refresh Token 검증
    private boolean validateRefreshToken(String refreshToken) {
        String email = jwtUtil.getEmail(refreshToken);
        Optional<RefreshToken> storedRefreshToken = refreshTokenRepository.findById(email);
        return storedRefreshToken.isPresent() && storedRefreshToken.get().getRefreshToken().equals(refreshToken);
    }

    // 쿠키 설정 메소드
    private void setTokenCookie(HttpServletResponse response, String name, String token, int maxAge) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키 설정 메소드
    private void setTokenCookie(HttpServletResponse response, String name, String token) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    // Unauthorized 응답 전송 메소드
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\": false, \"message\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
