package com.example.api.global.security.oauth2;

import com.example.api.global.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

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
        Collection<? extends GrantedAuthority> authorities = oAuth2Member.getAuthorities();
        for (GrantedAuthority authority : authorities){
            if(Objects.equals(authority.toString(), role)){
                continue;
            }
            Cookie viToken = new Cookie("VITOKEN"+ UUID.randomUUID(), jwtUtil.createToken(authority.getAuthority()));
            viToken.setHttpOnly(true);
            viToken.setSecure(true);
            viToken.setPath("/");
            viToken.setMaxAge(60 * 60);
            response.addCookie(viToken);
        }
        Cookie aToken = new Cookie("ATOKEN", token);
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
        aToken.setMaxAge(60 * 60);
        response.addCookie(aToken);
        getRedirectStrategy().sendRedirect(request, response, "https://www.sabujaks-irs.kro.kr/");
    }
}
