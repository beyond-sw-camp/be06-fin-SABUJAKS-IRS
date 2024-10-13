package com.example.api.domain.system.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRes {
    private String code; // 대분류 코드
    private String description; // 대분류 설명
    private List<SubcategoryRes> subcategories; // 소분류 리스트
}
