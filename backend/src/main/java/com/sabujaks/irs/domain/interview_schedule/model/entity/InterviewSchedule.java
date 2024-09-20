package com.sabujaks.irs.domain.interview_schedule.model.entity;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluate;
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

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String careerBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_idx")
    private Recruiter recruiter;

    @ManyToOne
    @JoinColumn(name = "announcement_idx")
    private Announcement announcement;

    @OneToMany(mappedBy = "interviewSchedule")
    private List<InterviewParticipate> interviewParticipateList;

    @OneToMany(mappedBy = "interviewSchedule")
    private List<ReSchedule> reScheduleList;
  
    @OneToMany(mappedBy = "interviewSchedule", fetch = FetchType.LAZY)
    private List<InterviewEvaluate> interviewEvaluateList;
}
