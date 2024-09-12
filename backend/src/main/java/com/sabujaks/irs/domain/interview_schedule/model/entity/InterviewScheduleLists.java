package com.sabujaks.irs.domain.interview_schedule.model.entity;

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
public class InterviewScheduleLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interviewSchedule_idx")
    private InterviewSchedule interviewSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seeker_idx")
    private Seeker seeker;

}
