package com.service.member.service;

import com.service.common.dto.feign.*;
import com.service.common.dto.kafka.CreateEstimatorMsg;
import com.service.common.dto.kafka.CreatedEmailVerificationMsg;
import com.service.common.base.BaseException;
import com.service.common.base.BaseResponseMessage;
import com.service.member.communication.MemberFeignClient;
import com.service.member.communication.MemberKafkaProducer;
import com.service.common.dto.kafka.CreateEmailVerificationMsg;
import com.service.common.dto.kafka.DeleteVerificationMsg;
import com.service.member.entity.Estimator;
import com.service.member.entity.Recruiter;
import com.service.member.entity.Seeker;
import com.service.common.dto.request.member.EditPasswordReq;
import com.service.common.dto.request.member.SignupReq;
import com.service.common.dto.response.member.SignupRes;
import com.service.member.repository.EstimatorRepository;
import com.service.member.repository.RecruiterRepository;
import com.service.member.repository.SeekerRepository;
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
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final RecruiterRepository recruiterRepository;
    private final SeekerRepository seekerRepository;
    private final EstimatorRepository estimatorRepository;
    private final MemberKafkaProducer memberKafkaProducer;
    private final MemberFeignClient memberFeignClient;

    public SignupRes signup(SignupReq dto, String fileUrl) throws BaseException {
        if(Objects.equals(dto.getRole(), "ROLE_SEEKER")) { // 지원자로 회원가입 하는 경우
            // 채용담당자로 동일 이메일을 사용해서 가입한 경우
            if(recruiterRepository.findByRecruiterEmail(dto.getEmail()).isPresent()) {
                throw new BaseException(BaseResponseMessage.AUTH_SIGNUP_FAIL_REGISTER_AS_RECRUITER);
            }
            Optional<Seeker> result = seekerRepository.findBySeekerEmail(dto.getEmail());
            if(result.isPresent()){ // 지원자가 있는 경우
                Seeker seeker = result.get();
                // 이메일 인증 0 비활성화 1
                if(!seeker.getIsEmailAuth() && seeker.getIsInactive()) {
                    memberKafkaProducer.createEmailVerificationMsg(CreateEmailVerificationMsg.builder()
                            .email(dto.getEmail())
                            .isInactive(seeker.getIsInactive())
                            .role(seeker.getRole())
                            .build()
                    );
                    return SignupRes.builder().idx(seeker.getIdx()).build();
                } else {
                    throw new BaseException(BaseResponseMessage.AUTH_SIGNUP_FAIL_USER_ALREADY_EXITS);
                }
            }
            else {  // 지원자가 없는 경우
                Seeker seeker = Seeker.builder()
                        .role(dto.getRole())
                        .isEmailAuth(false)
                        .isInactive(false)
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .nickname(dto.getNickname())
                        .gender(dto.getGender())
                        .birth(dto.getBirth())
                        .address(dto.getAddress())
                        .phoneNumber(dto.getPhoneNumber())
                        .profileImage(fileUrl)
                        .socialType(dto.getSocialType())
                        .build();
                seekerRepository.save(seeker);
                memberKafkaProducer.createEmailVerificationMsg(CreateEmailVerificationMsg.builder()
                        .email(dto.getEmail())
                        .isInactive(seeker.getIsInactive())
                        .role(seeker.getRole())
                        .build()
                );
                return SignupRes.builder().idx(seeker.getIdx()).build();
            }
        } else if(Objects.equals(dto.getRole(), "ROLE_RECRUITER")){ // 채용담당자로 회원가입 하는 경우
            // 지원자로 동일 이메일을 사용해서 가입한 경우
            if(seekerRepository.findBySeekerEmail(dto.getEmail()).isPresent()) {
                throw new BaseException(BaseResponseMessage.AUTH_SIGNUP_FAIL_USER_ALREADY_EXITS);
            }
            Optional<Recruiter> result = recruiterRepository.findByRecruiterEmail(dto.getEmail());
            if(result.isPresent()){ // 채용담당자가 있는 경우
                Recruiter recruiter = result.get();
                // 이메일 인증 0 비활성화 1
                memberKafkaProducer.createEmailVerificationMsg(CreateEmailVerificationMsg.builder()
                        .email(dto.getEmail())
                        .isInactive(recruiter.getIsInActive())
                        .role(recruiter.getRole())
                        .build()
                );
                if(!recruiter.getIsEmailAuth() && recruiter.getIsInActive()) {
                    return SignupRes.builder().idx(recruiter.getIdx()).build();
                } else {
                    throw new BaseException(BaseResponseMessage.AUTH_SIGNUP_FAIL_USER_ALREADY_EXITS);
                }
            } else { // 채용담당자가 없는 경우
                ReadVerificationRes readVerification = memberFeignClient.readVerification(dto.getEmail(), true);
                if(readVerification == null){
                    throw new BaseException(BaseResponseMessage.AUTH_SIGNUP_FAIL_NOT_COMPANY_AUTH);
                }
                Recruiter recruiter = Recruiter.builder()
                        .role(dto.getRole())
                        .isCompanyAuth(true)
                        .isEmailAuth(false)
                        .isInActive(false)
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .phoneNumber(dto.getPhoneNumber())
                        .build();
                recruiterRepository.save(recruiter);
                memberKafkaProducer.createEmailVerificationMsg(CreateEmailVerificationMsg.builder()
                        .email(dto.getEmail())
                        .isInactive(recruiter.getIsInActive())
                        .role(recruiter.getRole())
                        .build()
                );
                return SignupRes.builder().idx(recruiter.getIdx()).build();
            }
        }  else {
            throw new BaseException(BaseResponseMessage.AUTH_SIGNUP_FAIL_USER_TYPE_INVALID);
        }
    }

    public void activeMember(CreatedEmailVerificationMsg dto) throws BaseException {
        if(Objects.equals(dto.getRole(), "ROLE_SEEKER") && Objects.equals(dto.getIsEmailAuth(), true)) { // 지원자인 경우
            Seeker seeker = seekerRepository.findBySeekerEmail(dto.getEmail())
                    .orElseThrow( () -> new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_USER_NOT_FOUND));
            seeker.setIsEmailAuth(true);
            seeker.setIsInactive(false);
            seekerRepository.save(seeker);
        } else if(Objects.equals(dto.getRole(), "ROLE_RECRUITER") && Objects.equals(dto.getIsEmailAuth(), true)) { // 지원자인 경우
            Recruiter recruiter = recruiterRepository.findByRecruiterEmail(dto.getEmail())
                    .orElseThrow( () -> new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_USER_NOT_FOUND));
            recruiter.setIsEmailAuth(true);
            recruiter.setIsInActive(false);
            recruiterRepository.save(recruiter);
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_USER_TYPE_INVALID);
        }
    }

    public void editPassword(Long memberIdx, String memberRole, EditPasswordReq dto) throws BaseException {
        if(Objects.equals(memberRole, "ROLE_SEEKER")){ // 지원자인 경우
            Optional<Seeker> result = seekerRepository.findBySeekerIdx(memberIdx);
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
        } else if(Objects.equals(memberRole, "ROLE_RECRUITER")) { // 채용담당자인 경우
            Optional<Recruiter> result = recruiterRepository.findByRecruiterIdx(memberIdx);
            if(result.isPresent()) {
                Recruiter recruiter = result.get();
                if(passwordEncoder.matches(dto.getOriginPassword(), recruiter.getPassword())) {
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
    public void inActive(Long memberIdx, String memberRole) throws BaseException{
        if(Objects.equals(memberRole, "ROLE_SEEKER")){
            Optional<Seeker> result = seekerRepository.findBySeekerIdx(memberIdx);
            if(result.isPresent()){
                Seeker seeker = result.get();
                seeker.setIsEmailAuth(false);
                seeker.setIsInactive(true);
                if(seeker.getPassword() == null){
                    seekerRepository.save(seeker);
                } else {
                    seekerRepository.save(seeker);
                    memberKafkaProducer.deleteEmailVerificationMsg(
                            DeleteVerificationMsg.builder()
                                    .email(seeker.getEmail())
                                    .role(seeker.getRole())
                                    .build()
                    );
                }
            }
        } else if(Objects.equals(memberRole, "ROLE_RECRUITER")) {
            Optional<Recruiter> result = recruiterRepository.findByRecruiterIdx(memberIdx);
            if(result.isPresent()) {
                Recruiter recruiter = result.get();
                recruiter.setIsEmailAuth(false);
                recruiter.setIsInActive(true);
                recruiterRepository.save(recruiter);
                memberKafkaProducer.deleteEmailVerificationMsg(
                        DeleteVerificationMsg.builder()
                                .email(recruiter.getEmail())
                                .role(recruiter.getRole())
                                .build()
                );
            }
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_INACTIVE_USER_FAIL);
        }
    }

    public ReadMemberRes read(String memberEmail, String memberRole, Long seekerIdx, String estimatorEmail) throws BaseException {
        if(Objects.equals(memberRole, "ROLE_SEEKER")){
            Seeker seeker = seekerRepository.findBySeekerEmail(memberEmail)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
            return ReadSeekerRes.builder()
                    .idx(seeker.getIdx())
                    .email(seeker.getEmail())
                    .name(seeker.getName())
                    .role(memberRole)
                    .nickname(seeker.getNickname())
                    .gender(seeker.getGender())
                    .birth(seeker.getBirth())
                    .phoneNumber(seeker.getPhoneNumber())
                    .address(seeker.getAddress())
                    .socialType(seeker.getSocialType())
                    .profileImage(seeker.getProfileImage())
                    .createdAt(seeker.getCreatedAt())
                    .updatedAt(seeker.getUpdatedAt())
                    .build();
        } else if (Objects.equals(memberRole, "ROLE_RECRUITER")){
            if(seekerIdx!=null){
                Seeker seeker = seekerRepository.findBySeekerIdx(seekerIdx)
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
                return ReadSeekerRes.builder()
                        .idx(seeker.getIdx())
                        .email(seeker.getEmail())
                        .name(seeker.getName())
                        .role(memberRole)
                        .nickname(seeker.getNickname())
                        .gender(seeker.getGender())
                        .birth(seeker.getBirth())
                        .phoneNumber(seeker.getPhoneNumber())
                        .address(seeker.getAddress())
                        .socialType(seeker.getSocialType())
                        .profileImage(seeker.getProfileImage())
                        .createdAt(seeker.getCreatedAt())
                        .updatedAt(seeker.getUpdatedAt())
                        .build();
            }
            if(estimatorEmail != null){
                Estimator estimator = estimatorRepository.findByEstimatorEmail(memberEmail)
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
                return ReadEstimatorRes.builder()
                        .idx(estimator.getIdx())
                        .role(estimator.getRole())
                        .isEmailAuth(estimator.getIsEmailAuth())
                        .name(estimator.getName())
                        .email(estimator.getEmail())
                        .build();
            }
            Recruiter recruiter = recruiterRepository.findByRecruiterEmail(memberEmail)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
            return ReadRecruiterRes.builder()
                    .idx(recruiter.getIdx())
                    .name(recruiter.getName())
                    .role(recruiter.getRole())
                    .email(recruiter.getEmail())
                    .phoneNumber(recruiter.getPhoneNumber())
                    .build();
        } else if(Objects.equals(memberRole, "ROLE_ESTIMATOR")) {
            Estimator estimator = estimatorRepository.findByEstimatorEmail(memberEmail)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL));
            return ReadEstimatorRes.builder()
                    .idx(estimator.getIdx())
                    .role(estimator.getRole())
                    .isEmailAuth(estimator.getIsEmailAuth())
                    .name(estimator.getName())
                    .email(estimator.getEmail())
                    .build();
        }  else {
            throw new BaseException(BaseResponseMessage.AUTH_SEARCH_USER_INFO_FAIL);
        }
    }

    public void createEstimator(CreateEstimatorMsg message) throws BaseException {
        Optional<Estimator> resultEstimator = estimatorRepository.findByEstimatorEmail(message.getEmail());
        if (resultEstimator.isEmpty()) {
            Recruiter resultRecruiter = recruiterRepository.findByRecruiterIdx(message.getRecruiterIdx())
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_READ_FAIL_RECRUITER_INFO_NOT_FOUND));
            Estimator estimator = Estimator.builder()
                    .role(message.getRole())
                    .name(message.getName())
                    .email(message.getEmail())
                    .recruiter(resultRecruiter)
                    .password(passwordEncoder.encode(message.getPassword()))
                    .isEmailAuth(true)
                    .build();
            estimatorRepository.save(estimator);
        }
    }
}
