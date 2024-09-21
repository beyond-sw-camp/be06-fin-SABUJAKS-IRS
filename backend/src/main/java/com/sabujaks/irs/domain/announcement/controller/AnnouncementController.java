package com.sabujaks.irs.domain.announcement.controller;

import com.sabujaks.irs.domain.announcement.model.request.AnnouncementCreateReq;
import com.sabujaks.irs.domain.announcement.model.request.CustomFormCreateReq;
import com.sabujaks.irs.domain.announcement.model.response.AnnouncementCreateRes;
import com.sabujaks.irs.domain.announcement.model.response.CompanyInfoReadRes;
import com.sabujaks.irs.domain.announcement.model.response.CustomFormCreateRes;
import com.sabujaks.irs.domain.announcement.model.response.RecruiterInfoReadRes;
import com.sabujaks.irs.domain.announcement.service.AnnouncementService;
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

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final CloudFileUpload cloudFileUpload;

    @PostMapping("/create-step1")
    public ResponseEntity<BaseResponse<AnnouncementCreateRes>> createStepOne(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart AnnouncementCreateReq dto,
            @RequestPart MultipartFile file) throws BaseException {

        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
        Long recruiterIdx = customUserDetails.getIdx();

        String fileUrl = "";
        if(file.isEmpty()) {
            fileUrl = "";
        } else {
            fileUrl = cloudFileUpload.upload(file);
        }
        AnnouncementCreateRes response = announcementService.createAnnouncement(recruiterIdx, fileUrl, dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_SUCCESS, response));

    }


    @PostMapping("/create-step2")
    public ResponseEntity<BaseResponse<CustomFormCreateRes>> createStepTwo(
            @RequestBody CustomFormCreateReq dto) throws BaseException {
        //공고를 작성하고 step2로 넘어오면 -> 채용담당자(기본값)?, 공고idx가 넘어와야 함
        //@AuthenticationPrincipal CustomUserDetails customUserDetails, 매개변수에 추가하기 (추후 수정 예정)
//        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
//        Long recruiterIdx = customUserDetails.getIdx();

        //채용담당자도 넘길 필요가 있는지 고민
        CustomFormCreateRes response = announcementService.createCustomForm(dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_SUCCESS, response));
    }


    @GetMapping("/read-recruiter-info")
    public ResponseEntity<BaseResponse<RecruiterInfoReadRes>> readRecruiterInfo(
            Long recruiterIdx) throws BaseException {

//        @AuthenticationPrincipal CustomUserDetails customUserDetails 매개변수 추가
//        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
//        Long recruiterIdx = customUserDetails.getIdx();
        RecruiterInfoReadRes response = announcementService.readRecruiterInfo(recruiterIdx);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_SEARCH_USER_INFO_SUCCESS, response));
    }


    @GetMapping("/read-company-info")
    public ResponseEntity<BaseResponse<CompanyInfoReadRes>> readCompanyInfo(
            Long recruiterIdx) throws BaseException {

//        @AuthenticationPrincipal CustomUserDetails customUserDetails 매개변수 추가
//        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
//        Long recruiterIdx = customUserDetails.getIdx();
        CompanyInfoReadRes response = announcementService.readCompanyInfo(recruiterIdx);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_INFO_SUCCESS_REGISTER, response));
    }
}