package com.service.interview.entity;

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

    private Long announcementIdx;

    private String announcementUUID;

    private Long recruiterIdx;

    @Column(nullable = false)
    private Boolean isOnline;

    @Column(nullable = false)
    private String interviewDate;

    @Column(nullable = false)
    private String interviewStart;

    @Column(nullable = false)
    private String interviewEnd;

    @Column(nullable = false)
    private String interviewScheduleUUID;

    @Column(nullable = false)
    private String careerBase;

    @Column(nullable = false)
    private Integer interviewNum;

    @OneToMany(mappedBy = "interviewSchedule")
    private List<InterviewParticipate> interviewParticipateList;

    //    TODO:
    //    @OneToMany(mappedBy = "interviewSchedule")
    //    private List<Alarm> alarm;

    @OneToMany(mappedBy = "interviewSchedule")
    private List<InterviewReSchedule> interviewReScheduleList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interviewTeam_idx")
    private InterviewTeam interviewTeam;

}
