package com.example.api.domain.system.service;

import com.example.api.domain.system.model.response.BaseInfoReadRes;
import com.example.api.domain.system.model.response.CategoryRes;
import com.example.api.domain.system.model.response.SubcategoryRes;
import com.example.common.domain.system.model.entity.BaseInfo;
import com.example.common.domain.system.repository.BaseInfoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BaseInfoService {
    private final BaseInfoRepository baseInfoRepository;

    public BaseInfoService(BaseInfoRepository baseInfoRepository) {
        this.baseInfoRepository = baseInfoRepository;
    }

    public List<CategoryRes> getCategory(String keyword) {
        // 대분류 데이터 조회
        // 부모코드가 null이고 조회를 원하는 카테고리명이 코드안에 있을 때
        List<BaseInfo> resultBaseInfoList = baseInfoRepository.findAllByCodeContainingAndParentCodeIsNull(keyword);
        // 대분류 리스트
        List<CategoryRes> categoryResList = new ArrayList<>();

        for(BaseInfo category : resultBaseInfoList) {
            // 대분류의 소분류 데이터 조회
            // 대분류들의 코드를 소분류의 부모코드로 사용
            List<BaseInfo> resultSubBaseInfoList = baseInfoRepository.findAllByParentCode(category.getCode());
            // 소분류 리스트
            List<SubcategoryRes> subcategoryResList = new ArrayList<>();

            // 소분류 데이터를 DTO로 변환
            for (BaseInfo subcategory : resultSubBaseInfoList) {
                subcategoryResList.add(new SubcategoryRes(subcategory.getCode(), subcategory.getDescription()));
            }

            // 대분류 데이터를 DTO로 변환
            categoryResList.add(new CategoryRes(category.getCode(), category.getDescription(), subcategoryResList));

        }

        return categoryResList;
    }

    public List<BaseInfoReadRes> getInfo(String codes) {
        // codes가 쉼표를 포함하는지 확인하고, 포함되면 split, 그렇지 않으면 단일 코드로 리스트 생성
        List<String> codeList = codes.contains(",") ? Arrays.asList(codes.split(",")) : List.of(codes);

        List<BaseInfo> baseInfoList = baseInfoRepository.findByCodeIn(codeList);

        List<BaseInfoReadRes> baseInfoReadResList = new ArrayList<>();
        // 검색 결과를 BaseInfoReadRes 리스트로 변환하여 반환
        for (BaseInfo baseInfo : baseInfoList) {
            BaseInfoReadRes baseInfoReadRes = BaseInfoReadRes.builder()
                    .code(baseInfo.getCode())
                    .description(baseInfo.getDescription())
                    .groupName(baseInfo.getGroupName())
                    .parentCode(baseInfo.getParentCode())
                    .build();
            baseInfoReadResList.add(baseInfoReadRes);
        }
        return baseInfoReadResList;
    }
}
