package com.example.api.domain.search.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterDto {
    private String name;  // 필터 이름 (예: "기업형태", "채용형태")
    private String value; // 필터 값 (예: "대기업", "신입")
}