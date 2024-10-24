package com.example.api.global.config;

import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.announcement.repository.AnnouncementRepository;
import com.example.common.domain.company.model.entity.Company;
import com.example.common.domain.company.repository.CompanyRepository;
import com.example.common.domain.resume.model.entity.Resume;
import com.example.common.domain.resume.repository.ResumeRepository;
import com.example.common.domain.total_process.model.entity.TotalProcess;
import com.example.common.domain.total_process.repository.TotalProcessRepository;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ResultSendingScheduler {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final AnnouncementRepository announcementRepository;
    private final CompanyRepository companyRepository;
    private final TotalProcessRepository totalProcessRepository;
    private final ResumeRepository resumeRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    @Scheduled(cron = "0 */5 * * * ?")
    public void sendResultPeriodically() {
        try {
            System.out.println("@@@@@@@@@@@@@@@@@@");
            System.out.println("Start Scheduler");
            System.out.println("@@@@@@@@@@@@@@@@@@");
            List<Announcement> announcementList = announcementRepository.findAllByDeadlineDocument(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            if(announcementList.isEmpty()) {
                System.out.println("리스트가 비어있습니다. 작업을 중지합니다.");
                return ;
            } else {
                for(Announcement announcement : announcementList) {
                    Optional<List<Resume>> resumeList = resumeRepository.findAllByAnnouncementIdx(announcement.getIdx());
                    if (resumeList.isPresent() && !resumeList.get().isEmpty()) {
                        for(Resume resume : resumeList.get()) {
                            Optional<Alarm> optionalAlarm = alarmRepository.findByResumeIdx(resume.getIdx());
                            if (optionalAlarm.isPresent()) {
                                continue;
                            } else {
                                MimeMessage message = mailSender.createMimeMessage();
                                MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                                System.out.println(resume.getSeeker().getEmail());
                                helper.setTo(resume.getSeeker().getEmail());
                                helper.setSubject("[IRS] 서류전형 결과 안내");

                                // 템플릿 내부에서 처리한 변수값 매핑
                                Company company = companyRepository.findByRecruiterIdx(resume.getAnnouncement().getRecruiter().getIdx()).get();
                                Map<String, Object> model = new HashMap<>();
                                model.put("name", resume.getSeeker().getName());
                                model.put("companyName", company.getName());
                                model.put("announcementTitle", resume.getAnnouncement().getTitle());

                                // 메일로 전송할 템플릿 렌더링
                                // 디렉토리 지정한 configure파일에서 객체 얻어와서 해당 객체로 템플릿 찾아서 얻어온다.
                                Optional<TotalProcess> totalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(resume.getAnnouncement().getIdx(), resume.getSeeker().getIdx());
                                Template template = null;
                                if(totalProcess.isPresent()) {
                                    if(totalProcess.get().getResumeResult()) {
                                        template = freeMarkerConfigurer.getConfiguration().getTemplate("ResumeAcceptEmail.html");
                                    } else {
                                        template = freeMarkerConfigurer.getConfiguration().getTemplate("ResumeRejectEmail.html");
                                    }
                                }

                                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                                helper.setText(html, true); // Set HTML content

                                mailSender.send(message);

                                alarmRepository.save(Alarm.builder()
                                        .type("서류전형 결과 안내")
                                        .status(false)
                                        .message(html)
                                        .seeker(resume.getSeeker())
                                        .createdAt(LocalDateTime.now())
                                        .resume(Resume.builder()
                                                .idx(resume.getIdx())
                                                .build())
                                        .build());
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("지원자 결과 전송 중 에러 발생.");
        }
    }
}