package com.sabujaks.irs.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferentialEmpReadRes {
    private Boolean veterans;
    private Boolean protection;
    private Boolean subsidy;
    private Boolean disability;
    private String disabilityDegree;
    private Boolean military;
    private String militaryClass;
    private String militaryStart;
    private String militaryEnd;
    private String militaryType;
    private String militaryRank;
}
