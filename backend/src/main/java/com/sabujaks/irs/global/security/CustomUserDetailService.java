package com.sabujaks.irs.global.security;

import com.sabujaks.irs.domain.interview_schedule.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.EstimatorRepository;
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
    private final EstimatorRepository estimatorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Seeker> seekerOpt = seekerRepository.findBySeekerEmail(email);
        if (seekerOpt.isPresent()) {
            Seeker seeker = seekerOpt.get();
            return CustomUserDetails.builder()
                    .seeker(seeker)
                    .idx(seeker.getIdx())
                    .name(seeker.getName())
                    .email(seeker.getEmail())
                    .password(seeker.getPassword())
                    .role(seeker.getRole())
                    .emailAuth(seeker.getEmailAuth())
                    .interviewParticipateList(seeker.getInterviewParticipateList())
                    .build();
        }
        Optional<Estimator> estimatorOpt = estimatorRepository.findByEstimatorEmail(email);
        if (estimatorOpt.isPresent()) {
            Estimator estimator = estimatorOpt.get();
            return CustomUserDetails.builder()
                    .estimator(estimator)
                    .idx(estimator.getIdx())
                    .email(estimator.getEmail())
                    .password(estimator.getPassword())
                    .role(estimator.getRole())
                    .emailAuth(true)
                    .build();
        }
        Optional<Recruiter> recruiterOpt = recruiterRepository.findByRecruiterEmail(email);
        if (recruiterOpt.isPresent()) {
            Recruiter recruiter = recruiterOpt.get();
            return CustomUserDetails.builder()
                    .recruiter(recruiter)
                    .idx(recruiter.getIdx())
                    .name(recruiter.getName())
                    .email(recruiter.getEmail())
                    .password(recruiter.getPassword())
                    .role(recruiter.getRole())
                    .emailAuth(recruiter.getEmailAuth())
                    .announcementList(recruiter.getAnnouncementList())
                    .build();
        }
        return null;
    }
}
