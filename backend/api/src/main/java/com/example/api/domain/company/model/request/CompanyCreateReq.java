package com.example.api.domain.company.model.request;

import com.example.api.domain.company.model.entity.CompanyBenefits;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCreateReq {

    private String industry; // 산업

    private String name; // 기업 명

    private String type; // 기업구분

    private String companyInfo; // 기업소개

    private String capital; // 자본금

    private String totalEmp; // 사원수

    private String establishDate; // 설립일

    private String sales; // 매출액

    private String business; // 주요사업

    private String url; // 홈페이지url

    private String address; // 기업주소

    private List<String> companyBenefitsList = new ArrayList<>(); // 복리후생 리스트

}