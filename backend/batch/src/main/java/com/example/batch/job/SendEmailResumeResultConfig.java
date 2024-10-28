package com.example.batch.job;

import com.example.batch.processor.EmailResumeResultProcessor;
import com.example.batch.reader.EmailReader;
import com.example.batch.writer.EmailWriter;
import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.announcement.model.entity.Announcement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SendEmailResumeResultConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmailReader emailReader;
    private final EmailWriter emailWriter;
    private final EmailResumeResultProcessor emailResumeResultProcessor;

    @Bean
    public Job resumeResultJob() {
        return new JobBuilder("resumeResultJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(resumeResultStep())
                .build();
    }

    @Bean
    public Step resumeResultStep() {
        return new StepBuilder("resumeResultStep", jobRepository)
                .<Announcement, List<Alarm>>chunk(10, transactionManager) // 청크 사이즈 설정
                .reader(emailReader.resumeDeadlineReader())
                .processor(emailResumeResultProcessor)
                .writer(emailWriter)
                .transactionManager(transactionManager)
                .build();
    }
}