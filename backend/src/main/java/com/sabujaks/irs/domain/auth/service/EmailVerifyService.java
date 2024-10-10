package com.sabujaks.irs.domain.auth.service;

import com.sabujaks.irs.domain.auth.model.entity.EmailVerify;
import com.sabujaks.irs.domain.auth.model.response.AuthSignupRes;
import com.sabujaks.irs.domain.auth.repository.EmailVerifyRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.utils.email.service.EmailSenderSeeker;
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
    private final EmailSenderSeeker emailSenderSeeker;
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

    public String sendMail(AuthSignupRes response) throws MailException {
        String uuid = UUID.randomUUID().toString();
        emailSenderSeeker.signupEmail(response, uuid);
        return uuid;
    }
}
