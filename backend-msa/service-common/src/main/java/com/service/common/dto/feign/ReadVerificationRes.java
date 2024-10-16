package com.service.common.dto.feign;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// OpenFeign: 검증 서비스 한테 기업 인증 정보를 가져옴
public class ReadVerificationRes {
    private String crn; // 사업자 등록번호
    private String ceoName; // 대표자명
    private String openedAt; // 개업 일자
    private String recruiterEmail; // 채용 담당자 이메일
}
