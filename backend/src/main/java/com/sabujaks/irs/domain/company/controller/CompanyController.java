package com.sabujaks.irs.domain.company.controller;

import com.sabujaks.irs.domain.announcement.model.request.CustomFormCreateReq;
import com.sabujaks.irs.domain.company.model.request.CompanyCreateReq;
import com.sabujaks.irs.domain.company.model.response.CompanyCreateRes;
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

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CustomFormCreateReq>> createInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart CompanyCreateReq dto,
            @RequestPart(required = false) MultipartFile[] files) throws BaseException {

        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
        Long recruiterIdx = customUserDetails.getIdx();

        // 파일 URL 리스트
        List<String> fileUrlList = new ArrayList<String>();

        // 파일 배열 처리
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileUrl = cloudFileUpload.upload(file);
                    fileUrlList.add(fileUrl);
                }
            }
            companyService.saveCompanyImages(fileUrlList);
        }
        CompanyCreateRes response = companyService.createCompany(recruiterIdx, dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_INFO_SUCCESS, response));
    }
}