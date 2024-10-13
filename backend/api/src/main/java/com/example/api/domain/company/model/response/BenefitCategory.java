package com.example.api.domain.company.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenefitCategory {
    private String category; // 대분류
    private List<String> subcategories; // 소분류 리스트
}