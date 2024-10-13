package com.example.api.global.security;

import com.example.common.domain.auth.model.entity.Estimator;
import com.example.common.domain.auth.model.entity.Recruiter;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.auth.repository.EstimatorRepository;
import com.example.common.domain.auth.repository.RecruiterRepository;
import com.example.common.domain.auth.repository.SeekerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private final RecruiterRepository recruiterRepository;
    private final SeekerRepository seekerRepository;
    private final EstimatorRepository estimatorRepository;

    public CustomUserDetailService(RecruiterRepository recruiterRepository, SeekerRepository seekerRepository, EstimatorRepository estimatorRepository) {
        this.recruiterRepository = recruiterRepository;
        this.seekerRepository = seekerRepository;
        this.estimatorRepository = estimatorRepository;
    }

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
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}
