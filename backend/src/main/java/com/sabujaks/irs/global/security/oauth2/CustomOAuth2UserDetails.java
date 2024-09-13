package com.sabujaks.irs.global.security.oauth2;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2UserDetails implements UserDetails, OAuth2User {

    private final Seeker seeker;
    private Map<String, Object> attributes;

    public CustomOAuth2UserDetails(Seeker seeker, Map<String, Object> attributes) {
        this.seeker = seeker;
        this.attributes = attributes;
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
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return seeker.getRole();
            }
        });
        return collection;
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