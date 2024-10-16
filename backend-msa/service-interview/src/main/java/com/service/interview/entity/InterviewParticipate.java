package com.service.interview.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewParticipate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long seekerIdx;

    private String estimatorEmail;

    private Boolean status;

    @OneToMany(mappedBy = "interviewParticipate")
    private List<InterviewEvaluate> interviewEvaluateList;

    @ManyToOne
    @JoinColumn(name="interviewSchedule_idx")
    private InterviewSchedule interviewSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interviewTeam_idx")
    private InterviewTeam interviewTeam;
}
