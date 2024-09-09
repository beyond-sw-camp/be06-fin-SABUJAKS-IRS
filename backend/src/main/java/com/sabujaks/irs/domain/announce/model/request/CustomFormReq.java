package com.sabujaks.irs.domain.announce.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomFormReq {
    private List<String> code; // 맞춤 양식 코드 리스트
    // 예시)"code": ["A001", "A002", "A003"]
}
