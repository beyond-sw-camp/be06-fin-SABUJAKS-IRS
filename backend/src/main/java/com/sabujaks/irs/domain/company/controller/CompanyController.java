package com.sabujaks.irs.domain.company.controller;

import com.sabujaks.irs.domain.announce.model.request.CustomFormReq;
import com.sabujaks.irs.domain.company.model.request.CreateCompanyReq;
import com.sabujaks.irs.domain.company.model.response.CreateCompanyRes;
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
@RequestMapping("/api/mypage/recruiter")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CloudFileUpload cloudFileUpload;

    @RequestMapping(method = RequestMethod.POST, value = "/company/create")
    public ResponseEntity<BaseResponse<CustomFormReq>> createInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart CreateCompanyReq dto,
            @RequestPart MultipartFile[] files) throws BaseException {

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
        CreateCompanyRes response = companyService.createCompany(recruiterIdx, dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_INFO_SUCCESS, response));
    }
}
