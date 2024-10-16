package com.service.common.dto.feign;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadBenefitCategoryRes {
    private String category; // 대분류
    private List<String> subcategories; // 소분류 리스트
}