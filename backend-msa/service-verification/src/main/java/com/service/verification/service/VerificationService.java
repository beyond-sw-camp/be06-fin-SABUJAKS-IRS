package com.service.verification.service;


import com.service.common.dto.feign.ReadVerificationRes;
import com.service.common.dto.kafka.CreateEmailVerificationMsg;
import com.service.common.dto.kafka.CreatedEmailVerificationMsg;
import com.service.common.base.BaseException;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.kafka.DeleteVerificationMsg;
import com.service.verification.communication.CrnApiFeignClient;
import com.service.verification.communication.VerificationKafkaProducer;
import com.service.verification.entity.CompanyVerification;
import com.service.verification.entity.EmailVerification;
import com.service.common.dto.request.verification.VerifyCompanyReq;
import com.service.common.dto.feign.ValidateCrnApiReq;
import com.service.common.dto.feign.ValidateCrnApiRes;
import com.service.common.dto.response.verification.VerifyCompanyRes;
import com.service.verification.repository.CompanyVerificationRepository;
import com.service.verification.repository.EmailVerificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationService {

    @Value("${api-keys.company-registration-key}")
    private String crnKey;

    private final JavaMailSender javaMailSender;
    private final CrnApiFeignClient crnApiFeignClient;
    private final VerificationKafkaProducer verificationKafkaProducer;
    private final CompanyVerificationRepository companyVerificationRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    public void verifyEmail(String email, String role, String uuid) throws BaseException {
        EmailVerification emailVerification = emailVerificationRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL));
        if (emailVerification.getUuid().equals(uuid)) {
            verificationKafkaProducer.createdEmailVerificationMsg(CreatedEmailVerificationMsg.builder()
                    .email(email)
                    .role(role)
                    .isEmailAuth(true)
                    .build()
            );
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL);
        }
    }

    public void sendMail(CreateEmailVerificationMsg dto) throws BaseException, MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        if(Objects.equals(dto.getRole(),"ROLE_RECRUITER")){
            if(!dto.getIsInactive()){
                message.setSubject("IRS - 채용 담당자로 가입하신걸 환영합니다.");
            } else {
                message.setSubject("IRS - 채용 담당자 계정 복구 이메일 검증");
            }
        } else if (Objects.equals(dto.getRole(), "ROLE_SEEKER")) {
            if( !dto.getIsInactive()){
                message.setSubject("IRS - 지원자로 가입하신걸 환영합니다.");
            } else {
                message.setSubject("IRS - 지원자 계정 복구 이메일 검증");
            }
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_USER_TYPE_INVALID);
        }
        String uuid = UUID.randomUUID().toString();
        // TODO: 배포시 이메일 링크 수정
        message.setText("http://localhost:8080/api/verification/verify/email?email="+dto.getEmail()+"&role="+dto.getRole()+"&uuid="+uuid);
        javaMailSender.send(message);
        EmailVerification emailVerification = EmailVerification.builder()
                .email(dto.getEmail())
                .uuid(uuid)
                .build();
        emailVerificationRepository.save(emailVerification);
    }


    // TODO: 어드민이 등록한 기준코드테이블에서 해당 기업의 시크릿 코드를 참조해 값을 가져와 비교하는 방식으로 반환
    public VerifyCompanyRes verifyCompany(VerifyCompanyReq dto) throws BaseException {
        ValidateCrnApiReq.Business business = ValidateCrnApiReq.Business.builder()
                .b_no(dto.getCrn())
                .start_dt(dto.getOpenedAt())
                .p_nm(dto.getCeoName())
                .build();
        ValidateCrnApiReq validateCrnApiReq = ValidateCrnApiReq.builder()
                .businesses(List.of(business))
                .build();
        ValidateCrnApiRes validateCrnApiRes = crnApiFeignClient.getCrnInfo(crnKey, validateCrnApiReq);
        if (Objects.equals(validateCrnApiRes.getData().get(0).getValid(), "02") || Objects.equals(validateCrnApiRes.getData().get(0).getValid(), "01")){
            CompanyVerification companyVerification;
            if(companyVerificationRepository.findByRecruiterEmail(dto.getRecruiterEmail()).isPresent()){
                return VerifyCompanyRes.builder()
                        .recruiterEmail(dto.getRecruiterEmail())
                        .isCompanyAuth(true)
                        .build();
            } else {
                companyVerification = CompanyVerification.builder()
                        .crn(dto.getCrn())
                        .openedAt(dto.getOpenedAt())
                        .ceoName(dto.getCeoName())
                        .recruiterEmail(dto.getRecruiterEmail())
                        .companySecretCode(dto.getCompanySecretCode()) // 이부분은 admin이 관리해야함
                        .build();
                companyVerificationRepository.save(companyVerification);
                return VerifyCompanyRes.builder()
                        .recruiterEmail(dto.getRecruiterEmail())
                        .isCompanyAuth(true)
                        .build();
            }
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_COMPANY_VERIFY_FAIL);
        }
    }

    public ReadVerificationRes readVerification(String email, Boolean flag) throws BaseException {
        if(flag == true){
            CompanyVerification companyVerification = companyVerificationRepository.findByRecruiterEmail(email)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_REGISTER_FAIL_NOT_FOUND_VERIFICATION));
            return ReadVerificationRes.builder()
                    .crn(companyVerification.getCrn())
                    .openedAt(companyVerification.getOpenedAt())
                    .ceoName(companyVerification.getCeoName())
                    .recruiterEmail(companyVerification.getRecruiterEmail())
                    .build();
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteVerification(DeleteVerificationMsg message) {
        if(Objects.equals(message.getRole(), "ROLE_SEEKER")){
            emailVerificationRepository.deleteByEmail(message.getEmail());
        } else if (Objects.equals(message.getRole(),"ROLE_RECRUITER")){
            emailVerificationRepository.deleteByEmail(message.getEmail());
//            companyVerificationRepository.deleteByRecruiterEmail(message.getEmail());
        }
    }
}
