package com.sabujaks.irs.domain.announce.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomFormRes {
    private List<String> code; // 맞춤 양식 코드 리스트
//    private Long custom_form_idx; // 지원서 맞춤 양식 idx
}
