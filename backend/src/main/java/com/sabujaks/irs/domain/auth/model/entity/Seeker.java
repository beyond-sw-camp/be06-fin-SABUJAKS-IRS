package com.sabujaks.irs.domain.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 지원자 엔티티
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Seeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 100, unique = true)
    private String email; // 이메일
    private String password; // 비밀번호
    private String name; // 지원자명
    private String nickname; // 닉네임
    private Boolean gender; // 성별 0 남성, 1 여성
    private String birth; // 생년월일
    private String phoneNumber; // 전화 번호
    private String address; // 주소
    private Boolean emailAuth; // 이메일 인증
    private Boolean inactive; // 계정 비활성화 상태
    private String role; // 접근 권한
    private String socialType; // 소셜 계정 여부
    private String profileImage; // 프로필 이미지

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
