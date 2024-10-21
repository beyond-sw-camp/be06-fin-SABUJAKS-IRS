package com.example.batch.processor;

import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import com.example.common.domain.company.model.entity.Company;
import com.example.common.domain.company.repository.CompanyRepository;
import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.interview_schedule.repository.InterviewParticipateRepository;
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
public class EmailInterviewScheduleInfoProcessor implements ItemProcessor<InterviewSchedule, List<Alarm>> {

    private final AlarmRepository alarmRepository;
    private final JavaMailSender mailSender;
    private final CompanyRepository companyRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public List<Alarm> process(InterviewSchedule interviewSchedule) throws Exception {
        Optional<List<InterviewParticipate>> interviewParticipateList = interviewParticipateRepository.findAllByInterviewScheduleIdx(interviewSchedule.getIdx());
        List<Alarm> alarmList = new ArrayList<>();

        Set<String> processedEmails = new HashSet<>();

        if (interviewParticipateList.isPresent()) {
            for (InterviewParticipate participate : interviewParticipateList.get()) {
                String email = participate.getSeeker().getEmail();
                if (processedEmails.contains(email)) {
                    continue;
                }

                processedEmails.add(email);

                Optional<Alarm> optionalAlarm = alarmRepository.findByInterviewScheduleIdx(participate.getInterviewSchedule().getIdx());

                if (optionalAlarm.isPresent()) {
                    continue;  // 이미 알람이 존재하면 건너뜀
                } else {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                    helper.setTo(email);
                    helper.setSubject("[IRS] 인터뷰 일정 안내");

                    // 템플릿 내부에서 처리한 변수값 매핑
                    Company company = companyRepository.findByRecruiterIdx(participate.getInterviewSchedule().getAnnouncement().getRecruiter().getIdx()).get();
                    Map<String, Object> model = new HashMap<>();
                    model.put("name", participate.getSeeker().getName());
                    model.put("interviewDate", participate.getInterviewSchedule().getInterviewDate());
                    model.put("interviewStart", participate.getInterviewSchedule().getInterviewStart());
                    model.put("interviewEnd", participate.getInterviewSchedule().getInterviewEnd());
                    model.put("companyName", company.getName());
                    model.put("announcementTitle", participate.getInterviewSchedule().getAnnouncement().getTitle());

                    model.put("isOnline", participate.getInterviewSchedule().getIsOnline() ? "온라인" : "오프라인");

                    // 메일로 전송할 템플릿 렌더링
                    Template template = freeMarkerConfigurer.getConfiguration()
                            .getTemplate(participate.getInterviewSchedule().getCareerBase().equals("경력") ? "InterviewExpEmail.html" : "InterviewNewEmail.html");

                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    helper.setText(html, true);  // HTML 내용 설정

                    mailSender.send(message);  // 메일 전송

                    // 알람 객체 생성

                    alarmList.add(Alarm.builder()
                            .type("인터뷰 일정 안내")
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

        return null;  // 더 이상 처리할 알람이 없으면 null 반환
    }
}
