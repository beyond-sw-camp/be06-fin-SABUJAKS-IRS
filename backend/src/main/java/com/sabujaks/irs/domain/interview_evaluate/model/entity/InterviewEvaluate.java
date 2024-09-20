package com.sabujaks.irs.domain.interview_evaluate.model.entity;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
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
    private String status;
    private Long totalCount;

    @ManyToOne
    @JoinColumn(name="seeker_idx")
    private Seeker seeker;

    @ManyToOne
    @JoinColumn(name = "interviewSchedule_idx")
    private InterviewSchedule interviewSchedule;

    @ManyToOne
    @JoinColumn(name = "interviewEvaluateForm_idx")
    private InterviewEvaluateForm interviewEvaluateForm;

    @ManyToOne
    @JoinColumn(name = "interviewEvaluateResult_idx")
    private InterviewEvaluateResult interviewEvaluateResult;

}
