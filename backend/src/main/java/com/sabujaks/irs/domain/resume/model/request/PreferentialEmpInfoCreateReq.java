package com.sabujaks.irs.domain.resume.model.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferentialEmpInfoCreateReq {
    private Boolean veterans;
    private Boolean protection;
    private Boolean subsidy;
    private Boolean disability;
    private String disabilityDegree;
    private Boolean military;
    private String militaryClass;
    private LocalDate militaryStart;
    private LocalDate militaryEnd;
    private String militaryType;
    private String militaryRank;
}
