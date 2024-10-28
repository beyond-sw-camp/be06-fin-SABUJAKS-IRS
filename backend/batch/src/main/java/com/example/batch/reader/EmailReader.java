package com.example.batch.reader;

import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailReader {

    private final EntityManagerFactory entityManagerFactory;

    private String localTestDaysString() {
        return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String getDateAfter7DaysString() {
        return LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String DeadlineDateString() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // 지원서 결과 전송
    @Bean
    @StepScope
    public JpaPagingItemReader<Announcement> resumeDeadlineReader() {

        return new JpaPagingItemReaderBuilder<Announcement>()
                .name("resumeDeadLineReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Announcement a WHERE a.deadlineDocument = :currentDate")
                .parameterValues(Map.of("currentDate", DeadlineDateString()))
                .pageSize(10)
                .build();
    }

    // 인터뷰 일정
    @Bean
    @StepScope
    public JpaPagingItemReader<InterviewSchedule> interviewScheduleReader() {

        return new JpaPagingItemReaderBuilder<InterviewSchedule>()
                .name("interviewScheduleReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT is FROM InterviewSchedule is WHERE is.interviewDate = :currentDate")
                .parameterValues(Map.of("currentDate", getDateAfter7DaysString()))
//                .parameterValues(Map.of("currentDate", localTestDaysString()))
                .pageSize(10)
                .build();
    }

    // 최종 결과 이메일
    @Bean
    @StepScope
    public JpaPagingItemReader<Announcement> finalResultReader() {
        return new JpaPagingItemReaderBuilder<Announcement>()
                .name("finalResultReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Announcement a WHERE a.deadlineFinal = :currentDate")
                .parameterValues(Map.of("currentDate", DeadlineDateString()))
                .pageSize(10)
                .build();
    }
}