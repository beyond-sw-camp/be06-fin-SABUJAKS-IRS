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
    private List<Announcement> announcementList = new ArrayList<>();
    private List<InterviewParticipate> interviewParticipateList = new ArrayList<>();
    private Set<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(Seeker seeker, Recruiter recruiter, Estimator estimator, Long idx, String email, String role, String name, String password, Boolean emailAuth, List<Announcement> announcementList, List<InterviewParticipate> interviewParticipateList, Set<SimpleGrantedAuthority> authorities) {
        this.seeker = seeker;
        this.recruiter = recruiter;
        this.estimator = estimator;
        this.idx = idx;
        this.email = email;
        this.role = role;
        this.name = name;
        this.password = password;
        this.emailAuth = emailAuth;
        this.announcementList = announcementList;
        this.interviewParticipateList = interviewParticipateList;
        this.authorities = authorities;
    }

    public CustomUserDetails(Seeker seeker, Set<SimpleGrantedAuthority> authorities) {
        this.seeker = seeker;
        this.recruiter = null;
        this.estimator = null;
        this.idx = seeker.getIdx();
        this.email = seeker.getEmail();
        this.role = seeker.getRole();
        this.name = seeker.getName();
        this.password = seeker.getPassword();
        this.emailAuth = seeker.getEmailAuth();
        this.interviewParticipateList = seeker.getInterviewParticipateList();
        this.authorities = authorities;
    }

    public CustomUserDetails(Recruiter recruiter, Set<SimpleGrantedAuthority> authorities) {
        this.seeker = null;
        this.recruiter = recruiter;
        this.estimator = null;
        this.idx = recruiter.getIdx();
        this.email = recruiter.getEmail();
        this.role = recruiter.getRole();
        this.name = recruiter.getName();
        this.password = recruiter.getPassword();
        this.emailAuth = recruiter.getEmailAuth();
        this.announcementList = recruiter.getAnnouncementList();
        this.authorities = authorities;
    }

    public CustomUserDetails(Estimator estimator, Set<SimpleGrantedAuthority> authorities) {
        this.seeker = null ;
        this.recruiter = null;
        this.estimator = estimator;
        this.idx = estimator.getIdx();
        this.email = estimator.getEmail();
        this.role = estimator.getRole();
        this.name = null;
        this.password = estimator.getPassword();
        this.emailAuth = estimator.getEmailAuth();
        this.interviewParticipateList = estimator.getInterviewParticipateList();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role));

        if (Objects.equals(this.role, "ROLE_SEEKER")) {
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
        }
        if (Objects.equals(this.role, "ROLE_ESTIMATOR")) {
            if (estimator != null && estimator.getInterviewParticipateList() != null) {
                for (InterviewParticipate participate : estimator.getInterviewParticipateList()) {
                    String authority =
                            "ROLE_ESTIMATOR|" + participate.getInterviewSchedule().getAnnouncement().getUuid()
                                    + "|" + participate.getInterviewSchedule().getUuid();
                    authorities.add(new SimpleGrantedAuthority(authority));
                }
            }
        }
        if (Objects.equals(this.role, "ROLE_RECRUITER")) {
            if (recruiter != null && recruiter.getAnnouncementList() != null) {
                for (Announcement announcement : recruiter.getAnnouncementList()) {
                    String authority = "ROLE_RECRUITER|" + announcement.getUuid();
                    authorities.add(new SimpleGrantedAuthority(authority));
                }
            }
        }
        return authorities;
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