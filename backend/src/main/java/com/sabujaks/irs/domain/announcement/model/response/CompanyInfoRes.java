package com.sabujaks.irs.domain.announcement.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInfoRes {
    private List<BenefitCategory> benefitsDataList; // 복리후생 카테고리 리스트
}
