//package com.sabujaks.irs.global.security.filter;
//
//import com.sabujaks.irs.domain.auth.model.entity.RefreshToken;
//import com.sabujaks.irs.domain.auth.repository.RefreshTokenRepository;
//import com.sabujaks.irs.global.security.CustomUserDetailService;
//import com.sabujaks.irs.global.security.CustomUserDetails;
//import com.sabujaks.irs.global.utils.JwtUtil;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.filter.OncePerRequestFilter;
//import jakarta.servlet.http.Cookie;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@Slf4j
//@RequiredArgsConstructor
//public class RefreshTokenFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final CustomUserDetailService customUserDetailService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Exception exception = (Exception) request.getAttribute("exception");
//        String refreshToken = extractRefreshToken(request);
//
//        // Access Token이 없는 경우나 만료되지 않은 경우 필터 체인을 계속 진행
//        if (exception == null || !(exception instanceof ExpiredJwtException)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Access Token이 만료된 경우, Refresh Token 확인 및 갱신
//        if (refreshToken != null) {
//            String email = jwtUtil.getEmail(refreshToken);
//            Optional<RefreshToken> storedRefreshToken = refreshTokenRepository.findById(email);
//
//            if (storedRefreshToken.isPresent() && storedRefreshToken.get().getRefreshToken().equals(refreshToken)) {
//                // 저장된 Refresh Token과 일치하면 새로운 Access Token과 Refresh Token 발급
//                CustomUserDetails userDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(email);
//                String newAccessToken = jwtUtil.createToken(userDetails.getIdx(), userDetails.getEmail(), userDetails.getRole());
//                String newRefreshToken = jwtUtil.createRefreshToken(email);
//
//                // Redis에 저장된 Refresh Token 갱신
//                RefreshToken updatedRefreshToken = new RefreshToken(email, newRefreshToken);
//                refreshTokenRepository.save(updatedRefreshToken);
//
//                // 새 토큰을 쿠키로 설정
//                setTokenCookie(response, "ATOKEN", newAccessToken, 60 * 3);
//                setTokenCookie(response, "RTOKEN", newRefreshToken, 60 * 10);
//
//                // 새로운 Access Token을 기반으로 인증 정보 설정
//                Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//
//                // Access Token 갱신 후 필터 체인 실행
//                filterChain.doFilter(request, response);
//                return;
//            } else {
//                // 유효하지 않은 Refresh Token이거나 Redis에서 찾지 못한 경우
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.setContentType("application/json;charset=UTF-8");
//                response.getWriter().write("{\"success\": false, \"message\": \"세션이 유효하지 않습니다. 로그인을 다시 해 주세요.\"}");
//                response.getWriter().flush();
//                return;
//            }
//        }
//
//        // 필터 체인을 계속 진행
//        filterChain.doFilter(request, response);
//    }
//
//    private String extractAccessToken(HttpServletRequest request) {
//        // 쿠키에서 ATOKEN 추출
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("ATOKEN".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null; // 토큰이 없을 경우 null 반환
//    }
//
//    private String extractRefreshToken(HttpServletRequest request) {
//        // 쿠키에서 RTOKEN 추출
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("RTOKEN".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null; // 토큰이 없을 경우 null 반환
//    }
//
//    private void setTokenCookie(HttpServletResponse response, String name, String token, int maxAge) {
//        Cookie cookie = new Cookie(name, token);
//        cookie.setHttpOnly(true);  // JavaScript로 접근 불가
//        cookie.setSecure(true);    // HTTPS에서만 전송
//        cookie.setPath("/");       // 모든 경로에서 접근 가능
//        cookie.setMaxAge(maxAge);  // 쿠키의 유효 시간 설정 (초 단위)
//        response.addCookie(cookie);
//    }
//
//}
//
