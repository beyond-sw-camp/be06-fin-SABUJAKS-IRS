package com.sabujaks.irs.domain.auth.service;

import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.request.RecruiterSignupReq;
import com.sabujaks.irs.domain.auth.model.response.RecruiterSignupRes;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final RecruiterRepository recruiterRepository;

    public RecruiterSignupRes signup(RecruiterSignupReq dto) throws BaseException {
        if(Objects.equals(dto.getRole(), "ROLE_RECRUITER")){
            Optional<Recruiter> result = recruiterRepository.findByRecruiterEmail(dto.getEmail());
            if(result.isEmpty()){
                Recruiter recruiter = Recruiter.builder()
                        .role(dto.getRole())
                        .enabled(false)
                        .inactive(false)
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .phoneNumber(dto.getPhoneNumber())
                        .build();
                recruiterRepository.save(recruiter);
                return RecruiterSignupRes.builder()
                        .idx(recruiter.getIdx())
                        .build();
            } else {
                throw new BaseException(BaseResponseMessage.MEMBER_REGISTER_FAIL_MEMBER_ALREADY_EXITS);
            }
        } else if(Objects.equals(dto.getRole(), "ROLE_SEEKER")) {
            return null;
        } else {
            throw new BaseException(BaseResponseMessage.MEMBER_REGISTER_FAIL_INVALID_ROLE);
        }
    }
}