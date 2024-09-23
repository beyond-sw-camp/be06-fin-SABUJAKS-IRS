package com.sabujaks.irs.domain.interview_evaluate.model.entity;

import com.sabujaks.irs.domain.auth.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewParticipate;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
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
    private Boolean status;
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