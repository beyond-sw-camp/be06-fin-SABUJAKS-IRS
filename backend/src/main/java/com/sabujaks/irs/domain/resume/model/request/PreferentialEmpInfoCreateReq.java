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
    private String disability_degree;
    private Boolean military;
    private String military_class;
    private LocalDate military_start;
    private LocalDate military_end;
    private String military_type;
    private String military_rank;
}
