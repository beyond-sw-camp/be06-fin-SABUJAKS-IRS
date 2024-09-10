package com.sabujaks.irs.domain.announce.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomFormRes {
    private List<Long> baseInfoIdxList; // 기준코드표의 지원서 맞춤 양식 idx 리스트
    private List<String> descriptionList; // 맞춤 양식 코드 설명 리스트

    private List<Long> customFormLetterIdList; // 자기소개서 맞춤 양식 idx 리스트
}
