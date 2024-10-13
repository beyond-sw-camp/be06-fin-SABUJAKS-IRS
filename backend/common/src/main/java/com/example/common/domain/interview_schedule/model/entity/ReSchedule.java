package com.example.common.domain.interview_schedule.model.entity;

import com.example.common.domain.auth.model.entity.Seeker;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String interviewStart;

    @Column(nullable = false)
    private String interviewEnd;

    @Column(nullable = false)
    @Setter
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "interview_schedule_idx")
    private InterviewSchedule interviewSchedule;

    @ManyToOne
    @JoinColumn(name = "seeker_idx") // Seeker와의 관계 추가
    private Seeker seeker;
}
