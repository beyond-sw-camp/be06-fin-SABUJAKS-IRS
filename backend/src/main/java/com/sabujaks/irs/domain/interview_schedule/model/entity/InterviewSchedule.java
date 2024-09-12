package com.sabujaks.irs.domain.interview_schedule.model.entity;

import com.sabujaks.irs.domain.video_interview.mdoel.entity.VideoInterview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private String uuid;

    @Column(nullable = false)
    private String careerBase;

    @OneToMany(mappedBy = "interviewSchedule")
    private List<InterviewScheduleLists> interviewScheduleLists;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="videoInterview_idx")
    private VideoInterview videoInterviewRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_idx")
    private Team team;
}
