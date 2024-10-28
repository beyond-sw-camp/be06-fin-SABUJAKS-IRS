package com.example.api.global.security;

import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.auth.model.entity.Estimator;
import com.example.common.domain.auth.model.entity.Recruiter;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Builder
public class CustomUserDetails implements UserDetails {

    private final Seeker seeker;
    private final Recruiter recruiter;
    private final Estimator estimator;
    private final Long idx;
    private final String email;
    private final String role;
    private final String name;
    private final String password;
    private final Boolean emailAuth;

    public CustomUserDetails(Seeker seeker, Recruiter recruiter, Estimator estimator, Long idx, String email, String role, String name, String password, Boolean emailAuth) {
        this.seeker = seeker;
        this.recruiter = recruiter;
        this.estimator = estimator;
        this.idx = idx;
        this.email = email;
        this.role = role;
        this.name = name;
        this.password = password;
        this.emailAuth = emailAuth;
    }

    public CustomUserDetails(Seeker seeker) {
        this.seeker = seeker;
        this.recruiter = null;
        this.estimator = null;
        this.idx = seeker.getIdx();
        this.email = seeker.getEmail();
        this.role = seeker.getRole();
        this.name = seeker.getName();
        this.password = seeker.getPassword();
        this.emailAuth = seeker.getEmailAuth();
    }

    public CustomUserDetails(Recruiter recruiter) {
        this.seeker = null;
        this.recruiter = recruiter;
        this.estimator = null;
        this.idx = recruiter.getIdx();
        this.email = recruiter.getEmail();
        this.role = recruiter.getRole();
        this.name = recruiter.getName();
        this.password = recruiter.getPassword();
        this.emailAuth = recruiter.getEmailAuth();
    }

    public CustomUserDetails(Estimator estimator) {
        this.seeker = null ;
        this.recruiter = null;
        this.estimator = estimator;
        this.idx = estimator.getIdx();
        this.email = estimator.getEmail();
        this.role = estimator.getRole();
        this.name = null;
        this.password = estimator.getPassword();
        this.emailAuth = estimator.getEmailAuth();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority(){
            @Override
            public String getAuthority() {
                return role;
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return emailAuth;
    }
}