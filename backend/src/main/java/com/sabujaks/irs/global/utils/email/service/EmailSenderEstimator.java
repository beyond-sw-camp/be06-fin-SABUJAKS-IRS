package com.sabujaks.irs.global.utils.email.service;


import com.sabujaks.irs.domain.auth.model.response.SeekerInfoGetRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailSenderEstimator {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;

    public Boolean sendEmail(InterviewScheduleRes dto, List<String> estimatorList, String title) throws RuntimeException {
        try {
            for(SeekerInfoGetRes seekerInfoGetRes : dto.getSeekerList()) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                helper.setTo(seekerInfoGetRes.getEmail());
                helper.setSubject("[IRS] "+title);

                // 템플릿 내부에서 처리한 변수값 매핑
                Map<String, Object> model = new HashMap<>();
                model.put("name", seekerInfoGetRes.getName());

                // 메일로 전송할 템플릿 렌더링
                // 디렉토리 지정한 configure파일에서 객체 얻어와서 해당 객체로 템플릿 찾아서 얻어온다.
                Template template = freemarkerConfigurer.getConfiguration().getTemplate("InterviewScheduleInfo.html");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                helper.setText(html, true); // Set HTML content

                mailSender.send(message);
            }
            return true;
        } catch (Exception e) {
            new BaseResponse<>(BaseResponseMessage.EMAIL_SEND_FAIL);
        }
        return false;
    }
}