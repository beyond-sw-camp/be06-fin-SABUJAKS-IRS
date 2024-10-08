package com.sabujaks.irs.domain.system.controller;


import com.sabujaks.irs.domain.system.model.response.BaseInfoReadRes;
import com.sabujaks.irs.domain.system.model.response.CategoryRes;
import com.sabujaks.irs.domain.system.service.BaseInfoService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/base-info")
@RequiredArgsConstructor
public class BaseInfoController {
    private final BaseInfoService baseInfoService;

    // 기준정보에서 필요한 카테고리 대, 소 전체 조회 (카테고리 셀렉에 사용 가능)
    @GetMapping("/read/category")
    public ResponseEntity<BaseResponse<CategoryRes>> getCategory(String keyword) throws BaseException {

        List<CategoryRes> response = baseInfoService.getCategory(keyword);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.BASE_INFO_BENEFIT_CATEGORY_SEARCH_SUCCESS, response));
    }

    // 소분류 카테고리 조회 (모집직무 조회에 사용, 소분류로 대분류 설명과 소분류 설명 가져올때 사용)
    @GetMapping("/read/info")
    public ResponseEntity<BaseResponse<BaseInfoReadRes>> getInfo(String codes) throws BaseException {

        List<BaseInfoReadRes> response = baseInfoService.getInfo(codes);
        // 베이스 메세지 추가해서 바꾸기
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.BASE_INFO_BENEFIT_CATEGORY_SEARCH_SUCCESS, response));
    }
}
