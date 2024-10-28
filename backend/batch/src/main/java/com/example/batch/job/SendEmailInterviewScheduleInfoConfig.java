package com.example.batch.job;

import com.example.batch.processor.EmailInterviewScheduleInfoProcessor;
import com.example.batch.reader.EmailReader;
import com.example.batch.writer.EmailWriter;
import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
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
public class SendEmailInterviewScheduleInfoConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmailReader emailReader;
    private final EmailWriter emailWriter;
    private final EmailInterviewScheduleInfoProcessor emailInterviewScheduleInfoProcessor;

    @Bean
    public Job interviewScheduleInfoJob() {
        return new JobBuilder("interviewScheduleInfoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(interviewScheduleInfoStep())
                .build();
    }

    @Bean
    public Step interviewScheduleInfoStep() {
        return new StepBuilder("interviewScheduleStep", jobRepository)
                .<InterviewSchedule, List<Alarm>>chunk(10, transactionManager) // 청크 사이즈 설정
                .reader(emailReader.interviewScheduleReader())
                .processor(emailInterviewScheduleInfoProcessor)
                .writer(emailWriter)
                .transactionManager(transactionManager)
                .build();
    }
}
