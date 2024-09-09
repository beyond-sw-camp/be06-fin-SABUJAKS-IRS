package com.sabujaks.irs.global.security;

import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final RecruiterRepository recruiterRepository;
    private final SeekerRepository seekerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Seeker> resultSeeker =seekerRepository.findBySeekerEmail(email);
        if (resultSeeker.isPresent()) {
            Seeker seeker = resultSeeker.get();
            return CustomUserDetails.builder()
                    .idx(seeker.getIdx())
                    .name(seeker.getName())
                    .email(seeker.getEmail())
                    .password(seeker.getPassword())
                    .role(seeker.getRole())
                    .emailAuth(seeker.getEmailAuth())
                    .build();
        } else {
            Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterEmail(email);
            if (resultRecruiter.isPresent()) {
                Recruiter recruiter = resultRecruiter.get();
                return CustomUserDetails.builder()
                        .idx(recruiter.getIdx())
                        .name(recruiter.getName())
                        .email(recruiter.getEmail())
                        .password(recruiter.getPassword())
                        .role(recruiter.getRole())
                        .emailAuth(recruiter.getEmailAuth())
                        .build();
            } else {
                return null;
            }
        }
    }
}