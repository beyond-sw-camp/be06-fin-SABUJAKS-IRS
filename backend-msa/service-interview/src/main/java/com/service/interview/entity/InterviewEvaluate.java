package com.service.interview.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEvaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Integer totalScore;

    private String comments;

    @ManyToOne
    @JoinColumn(name = "interviewParticipate_idx")
    private InterviewParticipate interviewParticipate;

    @ManyToOne
    @JoinColumn(name = "interviewEvaluateForm_idx")
    private InterviewEvaluateForm interviewEvaluateForm;

    @ManyToOne
    @JoinColumn(name = "interviewEvaluateResult_idx")
    private InterviewEvaluateResult interviewEvaluateResult;

}