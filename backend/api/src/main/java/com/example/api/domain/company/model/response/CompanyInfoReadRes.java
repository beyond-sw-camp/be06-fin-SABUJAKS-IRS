package com.example.api.domain.company.model.response;

import com.example.api.domain.company.model.response.BenefitCategory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInfoReadRes {
    private List<BenefitCategory> benefitsDataList; // 복리후생 카테고리 리스트
}