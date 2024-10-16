package com.example.batch.processor;

import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.company.model.entity.Company;
import com.example.common.domain.company.repository.CompanyRepository;
import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.interview_schedule.repository.InterviewParticipateRepository;
import com.example.common.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.example.common.domain.video_interview.model.entity.VideoInterview;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class EmailConfirmInterviewScheduleProcessor implements ItemProcessor<InterviewSchedule, List<Alarm>> {

    private final AlarmRepository alarmRepository;
    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final CompanyRepository companyRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;


    @Override
    public List<Alarm> process(InterviewSchedule interviewSchedule) throws Exception {
        Optional<List<InterviewParticipate>> interviewParticipateList = interviewParticipateRepository.findAllByInterviewScheduleIdx(interviewSchedule.getIdx());
        List<Alarm> alarmList = new ArrayList<>();
        if(interviewParticipateList.isPresent()) {
            for(InterviewParticipate participate : interviewParticipateList.get()) {
                Optional<Alarm> optionalAlarm = alarmRepository.findByInterviewScheduleIdx(participate.getInterviewSchedule().getIdx());

                if (optionalAlarm.isPresent()) {
                    continue;
                } else {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                    helper.setTo(participate.getSeeker().getEmail());
                    helper.setSubject("[IRS] 인터뷰 일정 상세 안내");

                    // 템플릿 내부에서 처리한 변수값 매핑
                    Company company = companyRepository.findByRecruiterIdx(participate.getInterviewSchedule().getAnnouncement().getRecruiter().getIdx()).get();

                    Map<String, Object> model = new HashMap<>();
                    model.put("name", participate.getSeeker().getName());
                    model.put("interviewDate", participate.getInterviewSchedule().getInterviewDate());
                    model.put("interviewStart", participate.getInterviewSchedule().getInterviewStart());
                    model.put("interviewEnd", participate.getInterviewSchedule().getInterviewEnd());
                    model.put("companyName", company.getName());
                    model.put("announcementTitle", participate.getInterviewSchedule().getAnnouncement().getTitle());
                    model.put("videoInterviewUrl", "https://sabujaks-irs.kro.kr/video-interview/" + interviewSchedule.getAnnouncement().getUuid());

                    if(participate.getInterviewSchedule().getIsOnline()) {
                        model.put("isOnline", "온라인");
                    } else {
                        model.put("isOnline", "오프라인");
                    }

                    // 메일로 전송할 템플릿 렌더링
                    // 디렉토리 지정한 configure파일에서 객체 얻어와서 해당 객체로 템플릿 찾아서 얻어온다.
                    Template template = null;
                    if(participate.getInterviewSchedule().getCareerBase().equals("경력")) {
                        template = freeMarkerConfigurer.getConfiguration().getTemplate("InterviewExpEmail.html");
                    } else {
                        template = freeMarkerConfigurer.getConfiguration().getTemplate("InterviewNewEmail.html");
                    }

                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    helper.setText(html, true); // Set HTML content

                    mailSender.send(message);

                    alarmList.add(Alarm.builder()
                            .type("인터뷰 일정 상세 안내")
                            .status(false)
                            .message(html)
                            .seeker(participate.getSeeker())
                            .interviewSchedule(participate.getInterviewSchedule())
                            .createdAt(LocalDateTime.now())
                            .build());
                }
            }
            return alarmList;
        }
        return null;
    }
}
