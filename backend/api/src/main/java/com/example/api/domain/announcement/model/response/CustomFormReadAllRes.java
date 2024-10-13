package com.example.api.domain.announcement.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomFormReadAllRes {
    private List<String> customFormList; // 지원서 폼 리스트
    private List<String> customLetterList; // 자기소개서 문항 리스트
}
