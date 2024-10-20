package com.example.api.global.utils.email.service;

import com.example.api.domain.auth.model.response.SeekerInfoGetRes;
import com.example.api.domain.video_interview.model.response.VideoInterviewCreateRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.auth.model.entity.Estimator;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Async
@RequiredArgsConstructor
public class EmailSendEstimator {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;

    public void sendEstimatorSchedule(String estimatorPassword, Estimator estimator, InterviewSchedule interviewSchedule) throws RuntimeException {
        try {
                String email = estimator.getEmail();
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                helper.setTo(email);
                helper.setSubject("[IRS] 인터뷰 면접관 일정 상세 안내");
                // 템플릿 내부에서 처리한 변수값 매핑
                Map<String, Object> model = new HashMap<>();
                model.put("name", estimator.getName());
                model.put("interviewDate", interviewSchedule.getInterviewDate());
                model.put("interviewStart", interviewSchedule.getInterviewStart());
                model.put("interviewEnd", interviewSchedule.getInterviewEnd());
                model.put("id", estimator.getEmail());
                model.put("pw", estimatorPassword);
                model.put("announcementTitle", interviewSchedule.getAnnouncement().getTitle());
                model.put("videoInterviewUrl", "https://sabujaks-irs.kro.kr/video-interview/" +  interviewSchedule.getAnnouncement().getUuid());
//                model.put("videoInterviewUrl", "http://localhost:3000/api/api/video-interview/" +  interviewSchedule.getAnnouncement().getUuid());
                if (interviewSchedule.getIsOnline()) {
                    model.put("isOnline", "온라인");
                } else {
                    model.put("isOnline", "오프라인");
                }
                // 메일로 전송할 템플릿 렌더링
                Template template = freemarkerConfigurer.getConfiguration().getTemplate("EstimatorEmail.html");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                helper.setText(html, true); // Set HTML content
                mailSender.send(message);
        } catch (Exception e) {
            new BaseResponse<>(BaseResponseMessage.EMAIL_SEND_FAIL);
        }
    }
}
