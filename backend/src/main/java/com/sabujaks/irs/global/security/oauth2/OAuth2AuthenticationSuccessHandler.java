package com.sabujaks.irs.global.security.oauth2;

import com.sabujaks.irs.global.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2UserDetails oAuth2Member = (CustomOAuth2UserDetails) authentication.getPrincipal();
        Long idx = oAuth2Member.getIdx();
        String username = oAuth2Member.getUsername();
        String role = oAuth2Member.getSeeker().getRole();
        String token = jwtUtil.createToken(idx, username, role);
        log.info(idx + " " + role + " " + username);
        Cookie aToken = new Cookie("ATOKEN", token);
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
        aToken.setMaxAge(60 * 60);
        response.addCookie(aToken);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
