package com.sabujaks.irs.domain.auth.service;

import com.sabujaks.irs.domain.auth.model.entity.EmailVerify;
import com.sabujaks.irs.domain.auth.model.response.AuthSignupRes;
import com.sabujaks.irs.domain.auth.repository.EmailVerifyRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final JavaMailSender javaMailSender;
    private final EmailVerifyRepository emailVerifyRepository;

    public Boolean isExist(String email, String uuid) throws BaseException {
        EmailVerify emailVerify = emailVerifyRepository.findByEmail(email)
        .orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL));
        if (emailVerify.getUuid().equals(uuid)) {
            return true;
        } else {
            return false;
        }
    }

    public void save(String email, String uuid) throws BaseException{
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();
        emailVerifyRepository.save(emailVerify);
    }

    public String sendMail(AuthSignupRes response) throws BaseException, MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(response.getEmail());
        if(Objects.equals(response.getRole(),"ROLE_RECRUITER")){
            if(!response.getEmail_auth() && !response.getInactive()){
                message.setSubject("IRS - 채용 담당자로 가입하신걸 환영합니다.");
            } else {
                message.setSubject("IRS - 채용 담당자 계정 복구 이메일");
            }
        } else if (Objects.equals(response.getRole(), "ROLE_SEEKER")) {
            if(!response.getEmail_auth() && !response.getInactive()){
                message.setSubject("IRS - 지원자로 가입하신걸 환영합니다.");
            } else {
                message.setSubject("IRS - 지원자 계정 복구 이메일");
            }
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_INVALID_ROLE);
        }
        String uuid = UUID.randomUUID().toString();
        message.setText("http://localhost:8080/api/auth/email-verify?email="+response.getEmail()+"&role="+response.getRole()+"&uuid="+uuid);
        javaMailSender.send(message);
        return uuid;
    }
}
