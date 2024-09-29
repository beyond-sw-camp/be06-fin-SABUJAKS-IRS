package com.sabujaks.irs.domain.data_init.service;

import com.sabujaks.irs.domain.data_init.model.entity.BaseInfo;
import com.sabujaks.irs.domain.data_init.model.response.CategoryRes;
import com.sabujaks.irs.domain.data_init.model.response.SubcategoryRes;
import com.sabujaks.irs.domain.data_init.repository.BaseInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseInfoService {
    private final BaseInfoRepository baseInfoRepository;

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
}
