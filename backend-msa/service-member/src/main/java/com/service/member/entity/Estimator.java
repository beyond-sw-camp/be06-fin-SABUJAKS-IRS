package com.service.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
// 면접관 엔티티
public class Estimator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    private String email; // 이메일

    private String password; // 비밀번호

    private String name; // 이름

    private Boolean isEmailAuth; // 이메일 인증 여부

    private String role; // 접근 권한

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_idx")
    private Recruiter recruiter;

}
