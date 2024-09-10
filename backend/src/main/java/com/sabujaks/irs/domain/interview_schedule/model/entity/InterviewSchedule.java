package com.sabujaks.irs.domain.interview_schedule.model.entity;

import com.sabujaks.irs.domain.video_interview.mdoel.entity.VideoInterviewRoom;
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
public class InterviewSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Boolean isOnline;

    @Column(nullable = false)
    private String interviewDate;

    @Column(nullable = false)
    private String interviewStart;

    @Column(nullable = false)
    private String interviewEnd;

    @Column(nullable = false)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="videoInterview_idx")
    private VideoInterviewRoom videoInterviewRoom;
}
