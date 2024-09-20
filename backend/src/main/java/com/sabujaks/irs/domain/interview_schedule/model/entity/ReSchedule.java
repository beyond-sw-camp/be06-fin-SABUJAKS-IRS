package com.sabujaks.irs.domain.interview_schedule.model.entity;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "interview_schedule_idx")
    private InterviewSchedule interviewSchedule;

    @ManyToOne
    @JoinColumn(name = "seeker_idx") // Seeker와의 관계 추가
    private Seeker seeker;
}
