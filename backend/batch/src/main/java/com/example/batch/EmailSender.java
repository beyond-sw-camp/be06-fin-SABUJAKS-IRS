package com.example.batch;

import com.example.batch.processor.EmailResumeResultProcessor;
import com.example.batch.reader.EmailReader;
import com.example.batch.writer.EmailWriter;
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
public class EmailSender {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmailReader emailReader;
    private final EmailWriter emailWriter;
    private final EmailResumeResultProcessor emailProcessor;

    public EmailSender(JobRepository jobRepository, PlatformTransactionManager transactionManager, EmailReader emailReader, EmailWriter emailWriter, EmailResumeResultProcessor emailProcessor) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.emailReader = emailReader;
        this.emailWriter = emailWriter;
        this.emailProcessor = emailProcessor;
    }

    @Bean
    public Job emailJob() {
        return new JobBuilder("emailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(resumeDeadlineStep()) // 서류 마감일 관련 Step
                .next(interviewScheduleStep()) // 인터뷰 일정 관련 Step
                .next(finalResultStep()) // 최종 결과 관련 Step
                .build(); // end() 대신 build()로 처리
    }

    @Bean
    public Step resumeDeadlineStep() {
        return createStep("resumeDeadlineStep", emailReader.resumeDeadlineReader(), emailProcessor, emailWriter);
    }

    @Bean
    public Step interviewScheduleStep() {
        return createStep("interviewScheduleStep", emailReader.interviewScheduleReader(), emailProcessor, emailWriter);
    }

    @Bean
    public Step finalResultStep() {
        return createStep("finalResultStep", emailReader.finalResultReader(), emailProcessor, emailWriter);
    }

    private <T> Step createStep(String stepName, JpaPagingItemReader<T> reader, ItemProcessor<T, T> processor, ItemWriter<T> writer) {
        return new StepBuilder(stepName, jobRepository)
                .<T, T>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}