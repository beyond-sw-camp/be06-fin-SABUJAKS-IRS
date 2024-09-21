package com.sabujaks.irs.domain.auth.service;

import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.model.request.AuthSignupReq;
import com.sabujaks.irs.domain.auth.model.request.PasswordEditReq;
import com.sabujaks.irs.domain.auth.model.response.AuthSignupRes;
import com.sabujaks.irs.domain.auth.model.response.UserInfoGetRes;
import com.sabujaks.irs.domain.auth.repository.*;
import com.sabujaks.irs.domain.auth.model.entity.Estimator;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final RecruiterRepository recruiterRepository;
    private final SeekerRepository seekerRepository;
    private final EstimatorRepository estimatorRepository;
    private final CompanyVerifyRepository companyVerifyRepository;
    private final EmailVerifyRepository emailVerifyRepository;

    public AuthSignupRes signup(AuthSignupReq dto, String fileUrl) throws BaseException {
        if(Objects.equals(dto.getRole(), "ROLE_SEEKER")) {
            if(recruiterRepository.findByRecruiterEmail(dto.getEmail()).isPresent()) {
                throw new BaseException(BaseResponseMessage.AUTH_REGISTER_FAIL_ALREADY_REGISTER_AS_RECRUITER);
            }
            Optional<Seeker> result = seekerRepository.findBySeekerEmail(dto.getEmail());
            if(result.isPresent()){
                Seeker seeker = result.get();
                if(!seeker.getEmailAuth() && seeker.getInactive()) {
                    return AuthSignupRes.builder()
                            .idx(seeker.getIdx())
                            .role(seeker.getRole())
                            .email_auth(seeker.getEmailAuth())
                            .inactive(seeker.getInactive())
                            .email(seeker.getEmail())
                            .build();
                } else {
                    throw new BaseException(BaseResponseMessage.AUTH_REGISTER_FAIL_USER_ALREADY_EXITS);
                }
            }
            else {
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
            }
        } else if(Objects.equals(dto.getRole(), "ROLE_RECRUITER")){
            if(seekerRepository.findBySeekerEmail(dto.getEmail()).isPresent()) {
                throw new BaseException(BaseResponseMessage.AUTH_REGISTER_FAIL_ALREADY_REGISTER_AS_SEEKER);
            }
            Optional<Recruiter> result = recruiterRepository.findByRecruiterEmail(dto.getEmail());
            if(result.isPresent()){
                Recruiter recruiter = result.get();
                if(!recruiter.getEmailAuth() && recruiter.getInactive()) {
                    return AuthSignupRes.builder()
                            .idx(recruiter.getIdx())
                            .role(recruiter.getRole())
                            .email_auth(recruiter.getEmailAuth())
                            .inactive(recruiter.getInactive())
                            .email(recruiter.getEmail())
                            .build();
                } else {
                    throw new BaseException(BaseResponseMessage.AUTH_REGISTER_FAIL_USER_ALREADY_EXITS);
                }
            }
            else {
                if(companyVerifyRepository.findByRecruiterEmail(dto.getEmail()).isEmpty()){
                    throw new BaseException(BaseResponseMessage.AUTH_REGISTER_FAIL_NOT_COMPANY_AUTH);
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
        }  else {
            throw new BaseException(BaseResponseMessage.AUTH_REGISTER_FAIL_INVALID_ROLE);
        }
    }

    public Boolean activeUser(String email, String role) throws BaseException {
        if(Objects.equals(role, "ROLE_RECRUITER")){
            Recruiter recruiter = recruiterRepository.findByRecruiterEmail(email)
            .orElseThrow( () -> new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_NOT_FOUND));
            recruiter.setEmailAuth(true);
            recruiter.setInactive(false);
            recruiterRepository.save(recruiter);
        } else if(Objects.equals(role, "ROLE_SEEKER")) {
            Seeker seeker = seekerRepository.findBySeekerEmail(email)
                    .orElseThrow( () -> new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_NOT_FOUND));
            seeker.setEmailAuth(true);
            seeker.setInactive(false);
            seekerRepository.save(seeker);
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_INVALID_ROLE);
        }
        return true;
    }

    public UserInfoGetRes userInfo(CustomUserDetails customUserDetails) throws BaseException {
        if(Objects.equals(customUserDetails.getRole(), "ROLE_SEEKER")){
            Seeker seeker = seekerRepository.findBySeekerEmail(customUserDetails.getEmail())
            .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
            return UserInfoGetRes.builder()
                    .name(seeker.getName())
                    .email(seeker.getEmail())
                    .role(seeker.getRole())
                    .build();
        } else if (Objects.equals(customUserDetails.getRole(), "ROLE_ESTIMATOR")) {
            Estimator estimator = estimatorRepository.findByEstimatorEmail(customUserDetails.getEmail())
            .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
            return UserInfoGetRes.builder()
                    .name(estimator.getName())
                    .email(estimator.getEmail())
                    .role(estimator.getRole())
                    .build();
        } else if(Objects.equals(customUserDetails.getRole(), "ROLE_RECRUITER")){
            Seeker seeker = seekerRepository.findBySeekerIdx(customUserDetails.getIdx())
            .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
            return UserInfoGetRes.builder()
                    .name(seeker.getName())
                    .email(seeker.getEmail())
                    .role(seeker.getRole())
                    .build();
        } else {
            throw  new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL);
        }
    }

    public void editPassword(CustomUserDetails customUserDetails, PasswordEditReq dto) throws BaseException {
        if(Objects.equals(customUserDetails.getRole(), "ROLE_SEEKER")){
            Optional<Seeker> result = seekerRepository.findBySeekerIdx(customUserDetails.getIdx());
            if(result.isPresent()){
                Seeker seeker = result.get();
                if(passwordEncoder.matches(dto.getOriginPassword(), seeker.getPassword())){
                    seeker.setPassword(passwordEncoder.encode(dto.getNewPassword()));
                    seekerRepository.save(seeker);
                }
                else {
                    throw new BaseException(BaseResponseMessage.AUTH_EDIT_PASSWORD_FAIL_PASSWORD_NOT_MATCH);
                }
            }
        } else if(Objects.equals(customUserDetails.getRole(), "ROLE_RECRUITER")) {
            Optional<Recruiter> result = recruiterRepository.findByRecruiterIdx(customUserDetails.getIdx());
            if(result.isPresent()) {
                Recruiter recruiter = result.get();
                if(passwordEncoder.matches(dto.getOriginPassword(), recruiter.getPassword()))
                {
                    recruiter.setPassword(passwordEncoder.encode(dto.getNewPassword()));
                    recruiterRepository.save(recruiter);
                }
                else {
                    throw new BaseException(BaseResponseMessage.AUTH_EDIT_PASSWORD_FAIL_PASSWORD_NOT_MATCH);
                }
            }
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_EDIT_PASSWORD_FAIL);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void inactiveUser(CustomUserDetails customUserDetails) throws BaseException{
        if(Objects.equals(customUserDetails.getRole(), "ROLE_SEEKER")){
            Optional<Seeker> result = seekerRepository.findBySeekerIdx(customUserDetails.getIdx());
            if(result.isPresent()){
                Seeker seeker = result.get();
                seeker.setEmailAuth(false);
                seeker.setInactive(true);
                if(seeker.getPassword() == null){
                    seekerRepository.save(seeker);
                } else {
                    seekerRepository.save(seeker);
                    emailVerifyRepository.deleteByEmail(seeker.getEmail());
                }
            }
        } else if(Objects.equals(customUserDetails.getRole(), "ROLE_RECRUITER")) {
            Optional<Recruiter> result = recruiterRepository.findByRecruiterIdx(customUserDetails.getIdx());
            if(result.isPresent()) {
                Recruiter recruiter = result.get();
                recruiter.setEmailAuth(false);
                recruiter.setInactive(true);
                recruiterRepository.save(recruiter);
                emailVerifyRepository.deleteByEmail(recruiter.getEmail());
            }
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_INACTIVE_USER_FAIL);
        }
    }
}
