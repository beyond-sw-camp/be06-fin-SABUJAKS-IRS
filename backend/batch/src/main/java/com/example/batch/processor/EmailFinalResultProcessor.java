package com.example.batch.processor;

import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.company.model.entity.Company;
import com.example.common.domain.company.repository.CompanyRepository;
import com.example.common.domain.total_process.model.entity.TotalProcess;
import com.example.common.domain.total_process.repository.TotalProcessRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
@RequiredArgsConstructor
public class EmailFinalResultProcessor implements ItemProcessor<Announcement, List<Alarm>> {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final TotalProcessRepository totalProcessRepository;
    private final AlarmRepository alarmRepository;
    private final CompanyRepository companyRepository;

    @Override
    public List<Alarm> process(Announcement announcement) throws Exception {
        Optional<List<TotalProcess>> totalProcessList = totalProcessRepository.findAllByAnnouncementIdx(announcement.getIdx());
        List<Alarm> alarmList = new ArrayList<>();
        if (totalProcessList.isPresent()) {
            for(TotalProcess totalProcess : totalProcessList.get()) {
                Optional<Alarm> optionalAlarm = alarmRepository.findByTotalProcessIdx(totalProcess.getIdx());
                if (optionalAlarm.isPresent()) {
                    continue;
                } else {
                    if(totalProcess.getSeeker() == null) {
                        continue;
                    }
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                    helper.setTo(totalProcess.getSeeker().getEmail());
                    helper.setSubject("[IRS] 최종결과 안내");

                    // 템플릿 내부에서 처리한 변수값 매핑
                    Company company = companyRepository.findByRecruiterIdx(totalProcess.getAnnouncement().getRecruiter().getIdx()).get();
                    Map<String, Object> model = new HashMap<>();
                    model.put("name", totalProcess.getSeeker().getName());
                    model.put("companyName", company.getName());
                    model.put("announcementTitle", totalProcess.getAnnouncement().getTitle());
                    model.put("workDate", LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    model.put("companyAddress", company.getAddress());


                    // 메일로 전송할 템플릿 렌더링
                    // 디렉토리 지정한 configure파일에서 객체 얻어와서 해당 객체로 템플릿 찾아서 얻어온다.
                    Template template = null;
                    if(totalProcess.getFinalResult() == null) {
                        continue;
                    } else {
                        if(totalProcess.getFinalResult()) {
                            template = freeMarkerConfigurer.getConfiguration().getTemplate("FinalResultAcceptEmail.html");
                        } else {
                            template = freeMarkerConfigurer.getConfiguration().getTemplate("FinalResultRejectEmail.html");
                        }
                    }


                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    helper.setText(html, true); // Set HTML content

                    mailSender.send(message);

                    alarmList.add(Alarm.builder()
                            .type("최종결과 안내")
                            .status(false)
                            .message(html)
                            .seeker(totalProcess.getSeeker())
                            .createdAt(LocalDateTime.now())
                            .totalProcess(totalProcess)
                            .build());
                }
            }
            return alarmList;
        }
        return null;
    }
}