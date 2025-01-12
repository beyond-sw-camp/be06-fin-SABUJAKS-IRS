package com.example.api.global.utils.email.service;

import com.example.api.domain.auth.model.response.AuthSignupRes;
import com.example.api.domain.auth.model.response.SeekerInfoGetRes;
import com.example.api.domain.video_interview.model.response.VideoInterviewCreateRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.utils.email.response.ResumeResultRes;
import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.auth.repository.SeekerRepository;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.resume.model.entity.Resume;
import com.example.common.domain.total_process.model.entity.TotalProcess;
import com.example.common.domain.total_process.repository.TotalProcessRepository;
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
import java.util.*;

@Service
@Async
@RequiredArgsConstructor
public class EmailSenderSeeker {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;
    private final AlarmRepository alarmRepository;
    private final SeekerRepository seekerRepository;
    private final TotalProcessRepository totalProcessRepository;

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

    public void sendConfirmInterviewScheduleEmail(VideoInterviewCreateRes dto) throws RuntimeException {
        try {
            Set<String> sentEmails = new HashSet<>();

            for (SeekerInfoGetRes seekerInfoGetRes : dto.getInterviewScheduleRes().getSeekerList()) {
                String email = seekerInfoGetRes.getEmail();

                // 이미 처리된 이메일은 건너뛰기
                if (sentEmails.contains(email)) {
                    continue;
                }

                // 이메일을 처리한 것으로 기록
                sentEmails.add(email);

                System.out.println(email);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                helper.setTo(email);
                helper.setSubject("[IRS] 인터뷰 일정 상세 안내");

                // 템플릿 내부에서 처리한 변수값 매핑
                Map<String, Object> model = new HashMap<>();
                model.put("name", seekerInfoGetRes.getName());
                model.put("interviewDate", dto.getInterviewScheduleRes().getInterviewDate());
                model.put("interviewStart", dto.getInterviewScheduleRes().getInterviewStart());
                model.put("interviewEnd", dto.getInterviewScheduleRes().getInterviewEnd());
                model.put("companyName", dto.getInterviewScheduleRes().getCompanyName());
                model.put("announcementTitle", dto.getInterviewScheduleRes().getAnnouncementTitle());
                model.put("videoInterviewUrl", "https://www.sabujaks-irs.kro.kr/video-interview/" + dto.getAnnouncementUuid());

                if (dto.getInterviewScheduleRes().getIsOnline()) {
                    model.put("isOnline", "온라인");
                } else {
                    model.put("isOnline", "오프라인");
                }

                // 메일로 전송할 템플릿 렌더링
                Template template;
                if (dto.getInterviewScheduleRes().getCareerBase().equals("경력")) {
                    template = freemarkerConfigurer.getConfiguration().getTemplate("InterviewConfirmOnlineEmail.html");
                } else {
                    template = freemarkerConfigurer.getConfiguration().getTemplate("InterviewConfirmOnlineEmail.html");
                }

                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                helper.setText(html, true); // Set HTML content

                // Alarm 저장 로직
                Seeker seeker = seekerRepository.findBySeekerIdx(seekerInfoGetRes.getIdx())
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));

                Alarm alarm = Alarm.builder()
                        .type("인터뷰 일정 상세 안내")
                        .status(false)
                        .message(html)
                        .seeker(seeker)
                        .interviewSchedule(InterviewSchedule.builder()
                                .idx(dto.getInterviewScheduleRes().getIdx())
                                .isOnline(dto.getInterviewScheduleRes().getIsOnline())
                                .interviewDate(dto.getInterviewScheduleRes().getInterviewDate())
                                .interviewStart(dto.getInterviewScheduleRes().getInterviewStart())
                                .interviewEnd(dto.getInterviewScheduleRes().getInterviewEnd())
                                .uuid(dto.getInterviewScheduleRes().getUuid())
                                .careerBase(dto.getInterviewScheduleRes().getCareerBase())
                                .interviewNum(dto.getInterviewScheduleRes().getInterviewNum())
                                .build())
                        .createdAt(LocalDateTime.now())
                        .build();

                alarmRepository.save(alarm);
                mailSender.send(message);
            }
        } catch (Exception e) {
            new BaseResponse<>(BaseResponseMessage.EMAIL_SEND_FAIL);
        }
    }

    public void sendResumeResultEmail(List<ResumeResultRes> getInfo) throws RuntimeException {
        try {
            for(ResumeResultRes dto : getInfo) {
                Optional<Alarm> optionalAlarm = alarmRepository.findByResumeIdx(dto.getResumeIdx());
                if (optionalAlarm.isPresent()) {
                    continue; // checkAlarm이 존재하면 continue
                } else {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                    helper.setTo(dto.getSeekerEmail());
                    helper.setSubject("[IRS] 서류전형 결과 안내");

                    // 템플릿 내부에서 처리한 변수값 매핑
                    Map<String, Object> model = new HashMap<>();
                    model.put("name", dto.getSeekerName());
                    model.put("companyName", dto.getCompanyName());
                    model.put("announcementTitle", dto.getAnnouncementTitle());

                    // 메일로 전송할 템플릿 렌더링
                    // 디렉토리 지정한 configure파일에서 객체 얻어와서 해당 객체로 템플릿 찾아서 얻어온다.
                    TotalProcess totalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(dto.getAnnouncementIdx(), dto.getSeekerIdx()).get();
                    Template template = null;
                    if(totalProcess != null) {
                        if(totalProcess.getResumeResult()) {
                            template = freemarkerConfigurer.getConfiguration().getTemplate("ResumeAcceptEmail.html");
                        } else {
                            template = freemarkerConfigurer.getConfiguration().getTemplate("ResumeRejectEmail.html");
                        }
                    }

                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    helper.setText(html, true); // Set HTML content

                    // Alarm 저장 로직
                    Seeker seeker = seekerRepository.findBySeekerIdx(dto.getSeekerIdx())
                            .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));

                    Alarm alarm = Alarm.builder()
                            .type("서류전형 결과 안내")
                            .status(false)
                            .message(html)
                            .seeker(seeker)
                            .createdAt(LocalDateTime.now())
                            .resume(Resume.builder()
                                    .idx(dto.getResumeIdx())
                                    .build())
                            .build();

                    alarmRepository.save(alarm);

                    mailSender.send(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new BaseResponse<>(BaseResponseMessage.EMAIL_SEND_FAIL);
        }
    }
}