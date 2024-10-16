package com.service.common.dto.response.announcement;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadCategoryRes {
    private String code; // 대분류 코드
    private String description; // 대분류 설명
    private List<ReadSubcategoryRes> subcategories; // 소분류 리스트
}