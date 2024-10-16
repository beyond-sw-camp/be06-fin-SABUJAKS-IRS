package com.service.common.dto.response.announcement;

import com.service.common.dto.feign.ReadBenefitCategoryRes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadCompanyBenefitRes {
    private List<ReadBenefitCategoryRes> benefitsDataList; // 복리후생 카테고리 리스트
}