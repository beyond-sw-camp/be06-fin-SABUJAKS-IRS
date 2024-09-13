package com.sabujaks.irs.domain.company.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String url; // 기업사진 url

    private String createdAt; // 생성날짜

    private String updatedAt; // 수정날짜
}
