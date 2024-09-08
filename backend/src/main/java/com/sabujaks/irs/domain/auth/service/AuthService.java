package com.sabujaks.irs.domain.auth.service;

import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.model.request.AuthSignupReq;
import com.sabujaks.irs.domain.auth.model.response.AuthSignupRes;
import com.sabujaks.irs.domain.auth.repository.CompanyVerifyRepository;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
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
    private final SeekerRepository seekerRepository;
    private final CompanyVerifyRepository companyVerifyRepository;
    public AuthSignupRes signup(AuthSignupReq dto, String fileUrl) throws BaseException {
        if(Objects.equals(dto.getRole(), "ROLE_RECRUITER")){
            Optional<Recruiter> result = recruiterRepository.findByRecruiterEmail(dto.getEmail());
            if(result.isEmpty()){
                if(companyVerifyRepository.findByRecruiterEmail(dto.getEmail()).isEmpty()){
                    throw new BaseException(BaseResponseMessage.MEMBER_REGISTER_FAIL_NOT_COMPANY_AUTH);
                }
                Recruiter recruiter = Recruiter.builder()
                        .role(dto.getRole())
                        .companyAuth(true)
                        .emailAuth(false)
                        .inactive(false)
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .phoneNumber(dto.getPhone_number())
                        .build();
                recruiterRepository.save(recruiter);
                return AuthSignupRes.builder()
                        .idx(recruiter.getIdx())
                        .role(recruiter.getRole())
                        .email_auth(recruiter.getEmailAuth())
                        .inactive(recruiter.getInactive())
                        .email(recruiter.getEmail())
                        .build();
            }
            else {
                throw new BaseException(BaseResponseMessage.MEMBER_REGISTER_FAIL_MEMBER_ALREADY_EXITS);
            }
        } else if(Objects.equals(dto.getRole(), "ROLE_SEEKER")) {
            Optional<Seeker> result = seekerRepository.findBySeekerEmail(dto.getEmail());
            if(result.isEmpty()){
                Seeker seeker = Seeker.builder()
                        .role(dto.getRole())
                        .emailAuth(false)
                        .inactive(false)
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .nickname(dto.getNickname())
                        .gender(dto.getGender())
                        .birth(dto.getBirth())
                        .address(dto.getAddress())
                        .phoneNumber(dto.getPhone_number())
                        .profileImage(fileUrl)
                        .socialType(dto.getSocialType())
                        .build();
                seekerRepository.save(seeker);
                return AuthSignupRes.builder()
                        .idx(seeker.getIdx())
                        .role(seeker.getRole())
                        .email_auth(seeker.getEmailAuth())
                        .inactive(seeker.getInactive())
                        .email(seeker.getEmail())
                        .build();
            } else {
                throw new BaseException(BaseResponseMessage.MEMBER_REGISTER_FAIL_MEMBER_ALREADY_EXITS);
            }
        } else {
            throw new BaseException(BaseResponseMessage.MEMBER_REGISTER_FAIL_INVALID_ROLE);
        }
    }

    public Boolean activeMember(String email, String role) throws BaseException {
        if(Objects.equals(role, "ROLE_RECRUITER")){
            Recruiter recruiter = recruiterRepository.findByRecruiterEmail(email)
            .orElseThrow( () -> new BaseException(BaseResponseMessage.EMAIL_VERIFY_FAIL_NOT_FOUND));
            recruiter.setEmailAuth(true);
            recruiter.setInactive(false);
            recruiterRepository.save(recruiter);
        } else if(Objects.equals(role, "ROLE_SEEKER")) {
            Seeker seeker = seekerRepository.findBySeekerEmail(email)
                    .orElseThrow( () -> new BaseException(BaseResponseMessage.EMAIL_VERIFY_FAIL_NOT_FOUND));
            seeker.setEmailAuth(true);
            seeker.setInactive(false);
            seekerRepository.save(seeker);
        } else {
            throw new BaseException(BaseResponseMessage.EMAIL_VERIFY_FAIL_INVALID_ROLE);
        }
        return true;
    }
}
