package com.example.common.domain.auth.model.entity;

import com.example.common.domain.announcement.model.entity.Announcement;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 채용담당자 엔티티
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 100, unique = true)
    private String email; // 이메일

    @Setter
    @Column(nullable = false, length = 100)
    private String password; // 비밀번호
    private String name; // 가입자명
    private String phoneNumber; // 사업자 등록 번호
    private Boolean emailAuth; // 이메일 인증
    private Boolean companyAuth; // 기업 인증
    private Boolean inactive; // 계정 비활성화 상태
    private String role; // 접근 권한

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recruiter")
    private List<Announcement> AnnouncementList = new ArrayList<>(); // 공고 테이블과 관계

}
