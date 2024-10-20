package com.example.api.global.config;

import com.example.api.global.security.CustomUserDetailService;
import com.example.api.global.security.AccessControlService;
import com.example.api.global.security.exception.CustomAccessDeniedHandler;
import com.example.api.global.security.exception.CustomAuthenticationEntryPoint;
import com.example.api.global.security.exception.CustomLoginFailureHandler;
import com.example.api.global.security.filter.JwtFilter;
import com.example.api.global.security.filter.LoginFilter;
import com.example.api.global.security.oauth2.CustomOAuth2UserService;
import com.example.api.global.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.example.api.global.utils.JwtUtil;
import com.example.common.domain.auth.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailService customUserDetailService;
    private final AccessControlService accessControlService;

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
        http.addFilter(corsFilter());
        http.authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/api/test/ex03").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/interview-evaluate/create-form").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/interview-evaluate/search-form").hasAnyAuthority("ROLE_RECRUITER" ,"ROLE_ESTIMATOR")
                                .requestMatchers("/api/resume/create").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/resume/submit").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/resume/read").hasAnyAuthority("ROLE_SEEKER", "ROLE_RECRUITER")
                                .requestMatchers("/api/resume/read/submit-info").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/resume/read/integrated").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/resume/read-all").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/resume/recruiter/read-all").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/resume/update/docPassed/{resumeIdx}").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/announcement/recruiter/read-all/resume").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/announcement/create-step1").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/announcement/create-step2").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/announcement/read-all/see").permitAll()
                                .requestMatchers("/api/alarm/read-all").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/video-interview/create").hasAuthority("ROLE_RECRUITER")
                                .requestMatchers("/api/video-interview/read-all").access(accessControlService::hasVideoInterviewAccessAuthorities)
//                                .requestMatchers(HttpMethod.POST,"/api/video-interview/get-session-token").access(accessControlService::hasVideoInterviewAccessTimeAuthorities)
                                .requestMatchers("/api/auth/seeker/read").hasAuthority("ROLE_SEEKER")
                                .requestMatchers("/api/auth/user-info").hasAnyAuthority("ROLE_SEEKER", "ROLE_RECRUITER", "ROLE_ESTIMATOR")
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/interview-schedule/**").permitAll()
                                .anyRequest().permitAll()
        );

        http.oauth2Login((config) -> {
            config.successHandler(oAuth2AuthenticationSuccessHandler);
            config.userInfoEndpoint((endpoint) -> endpoint.userService(customOAuth2UserService));
        });
        http.logout((auth) ->
                auth
                        .logoutUrl("/api/auth/logout")
                        .deleteCookies("ATOKEN", "RTOKEN")
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"success\": true, \"message\": \"로그아웃 성공\"}");
                            response.getWriter().flush();
                        }))
        );
//        http.addFilter(corsFilter());
        http.exceptionHandling(e ->e.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler));
        http.addFilterBefore(new JwtFilter(jwtUtil, customUserDetailService, refreshTokenRepository), LoginFilter.class);
        LoginFilter loginFilter = new LoginFilter(jwtUtil, authenticationManager(authenticationConfiguration), refreshTokenRepository);
        loginFilter.setFilterProcessesUrl("/api/auth/login");
        loginFilter.setAuthenticationFailureHandler(customLoginFailureHandler);
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
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
