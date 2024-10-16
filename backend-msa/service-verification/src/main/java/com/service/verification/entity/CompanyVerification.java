package com.service.verification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
// 기업 인증 엔티티
public class CompanyVerification {

    // Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    private String crn; // 사업자 등록번호

    private String ceoName; // 대표자명

    private String openedAt; // 개업 일자

    private String recruiterEmail; // 채용 담당자 이메일

    private String companySecretCode; // 기업 전용 인증 코드

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // OneToMany

    // ManyToOne
}
