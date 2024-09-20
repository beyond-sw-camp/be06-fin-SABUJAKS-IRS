package com.sabujaks.irs.global.security;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import com.sabujaks.irs.domain.auth.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewParticipate;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private final Seeker seeker;
    private final Recruiter recruiter;
    private final Estimator estimator;
    private final Long idx;
    private final String email;
    private final String role;
    private final String name;
    private final String password;
    private final Boolean emailAuth;
    private List<InterviewParticipate> interviewParticipateList = new ArrayList<>();
    private List<Announcement> announcementList = new ArrayList<>();
    private Collection<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(Seeker seeker, Collection<SimpleGrantedAuthority> grantedAuthorities) {
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
        this.authorities = grantedAuthorities;
    }

    public CustomUserDetails(Recruiter recruiter, Collection<SimpleGrantedAuthority> grantedAuthorities) {
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
        this.authorities = grantedAuthorities;
    }

    public CustomUserDetails(Estimator estimator, Collection<SimpleGrantedAuthority> grantedAuthorities) {
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
        this.authorities = grantedAuthorities;
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

    public String getName(){ return name; }

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

    public Collection<? extends GrantedAuthority> getVideoInterviewAuthorities() { return authorities;}
}