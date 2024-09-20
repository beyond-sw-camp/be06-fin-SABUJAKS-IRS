package com.sabujaks.irs.global.utils;

import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewParticipateRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender emailSender;
    SimpleMailMessage message;

    public void sendEmail(InterviewScheduleRes response, List<String> emails){
        System.out.println("Sending email start");
        for(String email : emails) {
            message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[면접 URL 안내] 면접 URL 안내드립니다.");


//        message.setText("가입을 완료하시려면 아래 링크를 클릭해주세요."
//                + "http://3.36.126.48:8080/user/active?email=" + request.getEmail()+"&uuid="+uuid);  //Todo
            // /api/video-interview?announce-uuid=1234&interview-schedule=5678&type=estimator or participant&duration=“09:00-12:00”
            message.setText("면접 당일 안내드린 링크로 접속해주시기 바랍니다.\n" +
                    "http://localhost:8080/api/video-interview?announce=1234&interview-schedule=" +
                    response.getUuid() + "&type=estimator&duration=" + response.getInterviewStart() + "-" + response.getInterviewEnd());

            // 이메일 전송
            emailSender.send(message);
        }

        System.out.println("Sending email end");
    }
}

//import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewParticipateRes;
//import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class EmailSender {
//
//    private final JavaMailSender emailSender;
//    private final TemplateEngine templateEngine;  // Thymeleaf 템플릿 엔진 주입
//
//    public void sendEmail(InterviewScheduleRes response, List<String> emails) {
//        System.out.println("Sending email start");
//
//        for (String email : emails) {
//            try {
//                MimeMessage message = emailSender.createMimeMessage();
//                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//                helper.setTo(email);
//                helper.setSubject("[면접 URL 안내] 면접 URL 안내드립니다.");
//
//                // 템플릿에 전달할 데이터 설정
//                Context context = new Context();
//                context.setVariable("uuid", response.getUuid());
//                context.setVariable("interviewStart", response.getInterviewStart());
//                context.setVariable("interviewEnd", response.getInterviewEnd());
//
//                // 템플릿을 사용하여 HTML 생성
//                String htmlContent = templateEngine.process("interview-email", context);
//
//                helper.setText(htmlContent, true);
//
//                // 이메일 전송
//                emailSender.send(message);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Sending email end");
//    }
//}
