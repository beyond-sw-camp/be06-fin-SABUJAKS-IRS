package com.example.common.domain.alarm.model.entity;

import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.resume.model.entity.Resume;
import com.example.common.domain.total_process.model.entity.TotalProcess;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "seeker_idx")
    private Seeker seeker;

    @ManyToOne
    @JoinColumn(name = "interview_schedule_idx")
    private InterviewSchedule interviewSchedule;

    @ManyToOne
    @JoinColumn(name = "resume_idx")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "total_process_idx")
    private TotalProcess totalProcess;

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
