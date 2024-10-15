package com.example.batch.job;

import com.example.batch.processor.EmailResumeResultProcessor;
import com.example.batch.reader.EmailReader;
import com.example.batch.writer.EmailWriter;
import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SendEmailResumeResultConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmailReader emailReader;
    private final EmailWriter emailWriter;
    private final EmailResumeResultProcessor emailResumeResultProcessor;

    public SendEmailResumeResultConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, EmailReader emailReader, EmailWriter emailWriter, EmailResumeResultProcessor emailProcessor, EmailResumeResultProcessor emailResumeResultProcessor) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.emailReader = emailReader;
        this.emailWriter = emailWriter;
        this.emailResumeResultProcessor = emailResumeResultProcessor;
    }

    @Bean
    public Job interviewScheduleJob() {
        return new JobBuilder("interviewScheduleInfoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(interviewScheduleInfoStep())
                .build();
    }

    @Bean
    public Step interviewScheduleInfoStep() {
        return new StepBuilder("interviewScheduleStep", jobRepository)
                .<Announcement, Alarm>chunk(10, transactionManager) // 청크 사이즈 설정
                .reader(emailReader.resumeDeadlineReader())
                .processor(emailResumeResultProcessor)
                .writer(emailWriter)
                .transactionManager(transactionManager)
                .build();
    }
}