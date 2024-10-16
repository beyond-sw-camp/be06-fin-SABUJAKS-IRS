package com.example.api.domain.resume.model.request;

import lombok.*;

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
    private String militaryStart;
    private String militaryEnd;
    private String militaryType;
    private String militaryRank;
}