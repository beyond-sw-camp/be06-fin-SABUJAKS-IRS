package com.sabujaks.irs.domain.announcement.controller;

import com.sabujaks.irs.domain.announcement.model.request.AnnouncementCreateReq;
import com.sabujaks.irs.domain.announcement.model.request.CustomFormCreateReq;
import com.sabujaks.irs.domain.announcement.model.response.*;
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

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final CloudFileUpload cloudFileUpload;

    // 공고 등록1
    @PostMapping("/create-step1")
    public ResponseEntity<BaseResponse<AnnouncementCreateRes>> createStepOne(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestPart AnnouncementCreateReq dto,
        @RequestPart MultipartFile file) throws BaseException {

//        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
//        Long recruiterIdx = customUserDetails.getIdx();

        String fileUrl = "";
        if(file.isEmpty()) {
            fileUrl = "";
        } else {
            fileUrl = cloudFileUpload.upload(file);
        }
        AnnouncementCreateRes response = announcementService.createAnnouncement(customUserDetails, fileUrl, dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_SUCCESS, response));

    }

    // 공고 등록2 (지원서폼등록)
    @PostMapping("/create-step2")
    public ResponseEntity<BaseResponse<CustomFormCreateRes>> createStepTwo(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody CustomFormCreateReq dto) throws BaseException {

        //공고를 작성하고 step2로 넘어오면 -> 채용담당자(기본값)?, 공고idx가 넘어와야 함
        CustomFormCreateRes response = announcementService.createCustomForm(customUserDetails, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_SUCCESS, response));
    }

    // 공고 등록을 위한 조회1
    @GetMapping("/read-recruiter-info")
    public ResponseEntity<BaseResponse<RecruiterInfoReadRes>> readRecruiterInfo(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException {

        RecruiterInfoReadRes response = announcementService.readRecruiterInfo(customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_SEARCH_USER_INFO_SUCCESS, response));
    }

    // 공고 등록을 위한 조회2
    @GetMapping("/read-company-info")
    public ResponseEntity<BaseResponse<CompanyInfoReadRes>> readCompanyInfo(Long recruiterIdx) throws BaseException {

        CompanyInfoReadRes response = announcementService.readCompanyInfo(recruiterIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_INFO_SUCCESS_REGISTER, response));
    }

    // 공고 전체 목록 조회 (지원자 페이지에서)
    @GetMapping("/read-all/see")
    public ResponseEntity<BaseResponse<AnnouncementReadAllRes>> readAllSee() throws BaseException {

        List<AnnouncementReadAllRes> response = announcementService.readAllSee();
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_SEARCH_SUCCESS, response));
    }

    // 공고 상세 조회 (지원자 페이지에서)
    @GetMapping("/read-detail/see")
    public ResponseEntity<BaseResponse<AnnouncementReadDetailRes>> readDetailSee(Long announcementIdx) throws BaseException {

        AnnouncementReadDetailRes response = announcementService.readDetailSee(announcementIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_SEARCH_SUCCESS, response));
    }

    @GetMapping("/recruiter/read-all")
    public ResponseEntity<BaseResponse<AnnouncementReadAllRes>> readAllAnnouncement(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String careerBase) throws BaseException {
        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
        Long recruiterIdx = customUserDetails.getIdx();
        List<AnnouncementReadAllRes2> response = announcementService.readAllAnnouncement(recruiterIdx, careerBase);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_READ_ALL_SUCCESS, response));
    }
}