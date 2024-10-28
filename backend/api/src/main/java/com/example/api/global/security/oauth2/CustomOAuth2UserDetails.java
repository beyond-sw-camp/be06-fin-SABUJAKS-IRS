package com.example.api.global.security.oauth2;

import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class CustomOAuth2UserDetails implements UserDetails, OAuth2User {

    private final Seeker seeker;
    private final String role;
    private Set<SimpleGrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomOAuth2UserDetails(Seeker seeker, Map<String, Object> attributes, Set<SimpleGrantedAuthority> authorities) {
        this.seeker = seeker;
        this.authorities = authorities;
        this.attributes = attributes;
        this.role = seeker.getRole();
    }

    public Long getIdx(){
        return seeker.getIdx();
    }

    public String getRole() {
        return seeker.getRole();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return seeker.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role));
        if (seeker != null && seeker.getInterviewParticipateList() != null) {
            for (InterviewParticipate participate : seeker.getInterviewParticipateList()) {
                String authority =
                        "ROLE_SEEKER|" + participate.getInterviewSchedule().getAnnouncement().getUuid()
                                + "|" + participate.getInterviewSchedule().getUuid()
                                + "|" + participate.getInterviewSchedule().getInterviewDate()
                                + "|" + participate.getInterviewSchedule().getInterviewStart()
                                + "|" + participate.getInterviewSchedule().getInterviewEnd();
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return seeker.getPassword();
    }

    @Override
    public String getUsername() {
        return seeker.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return seeker.getEmailAuth();
    }
}