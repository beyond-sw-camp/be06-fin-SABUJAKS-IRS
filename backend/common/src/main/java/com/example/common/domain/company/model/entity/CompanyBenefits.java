package com.example.common.domain.company.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyBenefits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 20)
    private String code; // 복리 후생 코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_idx")
    private Company company; // 기업 외래키
}