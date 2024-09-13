package com.sabujaks.irs.global.config;

import com.sabujaks.irs.global.security.exception.CustomAccessDeniedHandler;
import com.sabujaks.irs.global.security.exception.CustomAuthenticationEntryPoint;
import com.sabujaks.irs.global.security.exception.CustomLoginFailureHandler;
import com.sabujaks.irs.global.security.filter.JwtFilter;
import com.sabujaks.irs.global.security.filter.LoginFilter;
import com.sabujaks.irs.global.security.oauth2.CustomOAuth2UserDetails;
import com.sabujaks.irs.global.security.oauth2.CustomOAuth2UserService;
import com.sabujaks.irs.global.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.sabujaks.irs.global.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());
        http.sessionManagement((auth) -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/api/test/ex01").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/video-interview/create").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/video-interview/room/**").access(this::hasAuthoritiesAboutVideoInterview)
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/interview-schedule/**").permitAll()
                                .anyRequest().permitAll()
        );
        http.addFilter(corsFilter());
        http.oauth2Login((config) -> {
            config.successHandler(oAuth2AuthenticationSuccessHandler);
            config.userInfoEndpoint((endpoint) -> endpoint.userService(customOAuth2UserService));
        });
        http.logout((auth) ->
                auth
                        .logoutUrl("/api/auth/logout")
                        .deleteCookies("ATOKEN")
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"success\": true, \"message\": \"로그아웃 성공\"}");
                            response.getWriter().flush();
                        }))
        );
        http.addFilter(corsFilter());
        http.exceptionHandling(e ->e.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler));
        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        LoginFilter loginFilter = new LoginFilter(jwtUtil, authenticationManager(authenticationConfiguration));
        loginFilter.setFilterProcessesUrl("/api/auth/login");
        loginFilter.setAuthenticationFailureHandler(customLoginFailureHandler);
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthorizationDecision hasAuthoritiesAboutVideoInterview(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        System.out.println(authentication.get().getAuthorities());
        System.out.println(object.getRequest().getRequestURI());
        String seekerAuthority =
                "ROLE_SEEKER" + object.getRequest().getParameter("announceUUID")
                + '_' + object.getRequest().getParameter("interviewScheduleUUID");
        String recruiterAuthority =
                "ROLE_RECRUITER_" + object.getRequest().getParameter("announceUUID");
        String estimatorAuthority =
                "ROLE_ESTIMATOR" + object.getRequest().getParameter("announceUUID")
                + '_' + object.getRequest().getParameter("interviewScheduleUUID");
        if( authentication.get().getAuthorities().contains(new SimpleGrantedAuthority(seekerAuthority))
            || authentication.get().getAuthorities().contains(new SimpleGrantedAuthority(recruiterAuthority))
            || authentication.get().getAuthorities().contains(new SimpleGrantedAuthority(estimatorAuthority))) {
            return new AuthorizationDecision(true);
        } else {
            return new AuthorizationDecision(false);
        }
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}