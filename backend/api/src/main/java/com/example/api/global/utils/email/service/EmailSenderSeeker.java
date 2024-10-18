package com.example.api.global.utils.email.service;

import com.example.api.domain.auth.model.response.AuthSignupRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Async
@RequiredArgsConstructor
public class EmailSenderSeeker {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;

    public void signupEmail(AuthSignupRes response, String uuid) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(response.getEmail());

            if(Objects.equals(response.getRole(),"ROLE_RECRUITER")){
                if(!response.getEmail_auth() && !response.getInactive()){
                    helper.setSubject("IRS - 채용 담당자로 가입하신걸 환영합니다.");
                } else {
                    helper.setSubject("IRS - 채용 담당자 계정 복구 이메일 검증");
                }
            } else if (Objects.equals(response.getRole(), "ROLE_SEEKER")) {
                if(!response.getEmail_auth() && !response.getInactive()){
                    helper.setSubject("IRS - 지원자로 가입하신걸 환영합니다.");
                } else {
                    helper.setSubject("IRS - 지원자 계정 복구 이메일 검증");
                }
            } else {
                throw new BaseException(BaseResponseMessage.AUTH_EMAIL_VERIFY_FAIL_INVALID_ROLE);
            }

            // 템플릿 내부에서 처리한 변수값 매핑
            Map<String, Object> model = new HashMap<>();
            model.put("name", response.getEmail());
//            model.put("emailVerifyUrl", "http://localhost:3000/api/api/auth/email-verify?email="+response.getEmail()+"&role="+response.getRole()+"&uuid="+uuid);
            model.put("emailVerifyUrl", "https://www.sabujaks-irs.kro.kr/api/api/auth/email-verify?email="+response.getEmail()+"&role="+response.getRole()+"&uuid="+uuid);

            Template template = freemarkerConfigurer.getConfiguration().getTemplate("SignupEmail.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(html, true); // Set HTML content
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            new BaseResponse<>(BaseResponseMessage.EMAIL_SEND_FAIL);
        }
    }
}