package com.example.common.domain.auth.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String crn; // 사업자 등록번호
    private String ceoName; // 대표자명
    private String openedAt; // 개업 일자
    private String recruiterEmail; // 채용 담당자 이메일
    private String companySecretCode; // 기업 전용 비밀 코드
}
