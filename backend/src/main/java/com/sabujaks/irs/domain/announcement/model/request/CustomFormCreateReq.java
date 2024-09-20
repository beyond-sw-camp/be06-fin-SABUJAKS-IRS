package com.sabujaks.irs.domain.announcement.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomFormCreateReq {
    private Long announceIdx;
    private List<String> code; // 맞춤 양식 코드 리스트
    // 예시)"code": ["resume_001", "resume_002", "resume_003"]

    private List<String> titleList; // 자기소개서 문항 리스트
    private List<Integer> chatLimitList; // 글자수 리스트
}
