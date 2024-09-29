package com.sabujaks.irs.domain.company.controller;

import com.sabujaks.irs.domain.announcement.model.request.CustomFormCreateReq;
import com.sabujaks.irs.domain.company.model.request.CompanyCreateReq;
import com.sabujaks.irs.domain.company.model.response.CompanyCreateRes;
import com.sabujaks.irs.domain.company.model.response.CompanyReadRes;
import com.sabujaks.irs.domain.company.service.CompanyService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.CloudFileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CloudFileUpload cloudFileUpload;

    // 기업 정보 조회
    @GetMapping("/read")
    public ResponseEntity<BaseResponse<CompanyReadRes>> read(
        @AuthenticationPrincipal CustomUserDetails customUserDetail) throws BaseException {

        CompanyReadRes response = companyService.readCompany(customUserDetail);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_INFO_SUCCESS_REGISTER, response));
    }

    // 기업 이미지 조회
    @GetMapping("/read-img")
    public ResponseEntity<BaseResponse<CompanyReadRes>> readImg(Long companyIdx) throws BaseException {

        CompanyReadRes response = companyService.readCompanyImg(companyIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_INFO_SUCCESS_REGISTER, response));
    }


    // 기업 정보 등록
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CompanyCreateRes>> createInfo(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestPart CompanyCreateReq dto,
        @RequestPart(required = false) MultipartFile[] files) throws BaseException {

        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
        Long recruiterIdx = customUserDetails.getIdx();
        CompanyCreateRes response = companyService.createCompany(recruiterIdx, dto);

        List<String> fileUrlList = cloudFileUpload.multipleUpload(files);
        companyService.saveCompanyImages(fileUrlList, response.getCompanyIdx());

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_INFO_SUCCESS, response));
    }
}