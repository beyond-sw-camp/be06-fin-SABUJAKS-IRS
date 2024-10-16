package com.service.common.dto.response.announcement;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadSubcategoryRes {
    private String code; // 소분류 코드
    private String description; // 소분류 설명
}
