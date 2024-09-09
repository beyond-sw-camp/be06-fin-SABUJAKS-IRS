package com.sabujaks.irs.domain.resume.model.request;

import com.sabujaks.irs.domain.resume.model.entity.PersonalInfo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeCreateReq {
    private PersonalInfoCreateReq personalInfo;
    private PreferentialEmpInfoCreateReq preferentialEmpInfo;
}
