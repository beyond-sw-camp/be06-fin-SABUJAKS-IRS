package com.service.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
// 채용 담당자 엔티티
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    @Column(nullable = false, length = 100, unique = true)
    private String email; // 이메일

    @Setter
    @Column(nullable = false, length = 100, unique = true)
    private String password; // 비밀번호

    private String name; // 이름

    private String phoneNumber; // 사업자 등록 번호

    private Boolean isCompanyAuth; // 기업 인증 여부

    private Boolean isEmailAuth; // 이메일 인증 여부

    private Boolean isInActive; // 계정 비활성화 여부

    private String role; // 시스템 권한

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "recruiter")
    private List<Estimator> estimator;
}
