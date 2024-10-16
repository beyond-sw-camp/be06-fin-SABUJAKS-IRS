package com.example.batch.job;

import com.example.batch.processor.EmailConfirmInterviewScheduleProcessor;
import com.example.batch.processor.EmailResumeResultProcessor;
import com.example.batch.processor.VideoInterviewProcessor;
import com.example.batch.reader.VideoInterviewReader;
import com.example.batch.writer.EmailWriter;
import com.example.batch.writer.VideoInterviewWriter;
import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.video_interview.model.entity.VideoInterview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class VideoInterviewConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final VideoInterviewReader videoInterviewReader;
    private final VideoInterviewProcessor videoInterviewProcessor;
    private final VideoInterviewWriter videoInterviewWriter;
    private final EmailConfirmInterviewScheduleProcessor emailConfirmInterviewScheduleProcessor;
    private final EmailWriter emailWriter;

    @Bean
    public Job videoInterviewJob() {
        return new JobBuilder("videoInterviewJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(videoInterviewStep()) // 비디오 방 생성 스텝
                .next(emailSendStep()) // 이메일 전송 스텝
                .build();
    }

    @Bean
    public Step videoInterviewStep() {
        return new StepBuilder("videoInterviewStep", jobRepository)
                .<InterviewSchedule, VideoInterview>chunk(10, transactionManager) // 청크 사이즈 설정
                .reader(videoInterviewReader.reader())
                .processor(videoInterviewProcessor)
                .writer(videoInterviewWriter)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Step emailSendStep() {
        return new StepBuilder("emailSendStep", jobRepository)
                .<InterviewSchedule, List<Alarm>>chunk(10, transactionManager)
                .reader(videoInterviewReader.reader()) // 이 부분은 아래에서 설명할게요.
                .processor(emailConfirmInterviewScheduleProcessor)
                .writer(emailWriter)
                .transactionManager(transactionManager)
                .build();
    }
}