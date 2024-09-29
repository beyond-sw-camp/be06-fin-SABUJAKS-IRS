package com.sabujaks.irs.domain.data_init.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50)
    private String groupName; // 대분류
    @Column(length = 50)
    private String code; // 퍼스널 코드
    @Column(length = 50)
    private String description; //설명
    @Column(length = 50)
    private String parentCode; // 상위 코드 (optional)
}
