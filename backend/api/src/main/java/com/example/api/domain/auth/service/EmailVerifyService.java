package com.example.api.domain.auth.service;

import com.example.api.domain.auth.model.response.AuthSignupRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.utils.email.service.EmailSenderSeeker;
import com.example.common.domain.auth.model.entity.EmailVerify;
import com.example.common.domain.auth.repository.EmailVerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailVerifyService {
    private final EmailSenderSeeker emailSenderSeeker;
    private final EmailVerifyRepository emailVerifyRepository;

    public EmailVerifyService(EmailSenderSeeker emailSenderSeeker, EmailVerifyRepository emailVerifyRepository) {
        this.emailSenderSeeker = emailSenderSeeker;
        this.emailVerifyRepository = emailVerifyRepository;
    }

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
