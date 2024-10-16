//package com.example.batch.job;
//
//import com.example.batch.processor.EmailFinalResultProcessor;
//import com.example.batch.reader.EmailReader;
//import com.example.batch.writer.EmailWriter;
//import com.example.common.domain.alarm.model.entity.Alarm;
//import com.example.common.domain.announcement.model.entity.Announcement;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Slf4j
//@Configuration
//public class SendEmailFinalResultConfig {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final EmailReader emailReader;
//    private final EmailWriter emailWriter;
//    private final EmailFinalResultProcessor emailFinalResultProcessor;
//

//
//    @Bean
//    public Job finalResultJob() {
//        return new JobBuilder("finalResultJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(finalResultStep())
//                .build();
//    }
//
//    @Bean
//    public Step finalResultStep() {
//        return new StepBuilder("finalResultStep", jobRepository)
//                .<Announcement, Alarm>chunk(10, transactionManager)
//                .reader(emailReader.finalResultReader())
//                .processor(emailFinalResultProcessor)
//                .writer(emailWriter)
//                .transactionManager(transactionManager)
//                .build();
//    }
//
//}