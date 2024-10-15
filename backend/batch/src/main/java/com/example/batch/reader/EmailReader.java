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

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailReader {

    private final EntityManagerFactory entityManagerFactory;

    //TODO
    // 서류전형은 resumeDeadline 날짜에 맞으면 처리
    // 인터뷰일정 안내는 interviewDate가 생성되면 5일전에 처리
    // 최종결과는 finalResultDate 날짜에 처리
    private String getCurrentDateString() {
        return java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Announcement> resumeDeadlineReader() {

        return new JpaPagingItemReaderBuilder<Announcement>()
                .name("resumeDeadLineReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Announcement a WHERE a.resumeDeadline = :currentDate")
                .parameterValues(Map.of("currentDate", getCurrentDateString()))
                .pageSize(10)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<InterviewSchedule> interviewScheduleReader() {

        return new JpaPagingItemReaderBuilder<InterviewSchedule>()
                .name("interviewScheduleReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT is FROM InterviewSchedule is WHERE is.interviewStart = :currentDate")
                .parameterValues(Map.of("currentDate", getCurrentDateString()))
                .pageSize(10)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Announcement> finalResultReader() {
        return new JpaPagingItemReaderBuilder<Announcement>()
                .name("finalResultReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Announcement a WHERE a.totalResultDeadline = :currentDate")
                .parameterValues(Map.of("currentDate", getCurrentDateString()))
                .pageSize(10)
                .build();
    }
}