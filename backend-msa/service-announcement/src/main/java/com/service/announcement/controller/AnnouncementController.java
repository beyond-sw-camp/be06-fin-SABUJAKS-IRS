package com.service.announcement.controller;


import com.service.announcement.config.CloudFileUpload;
import com.service.common.dto.feign.ReadCustomFormRes;
import com.service.common.dto.request.announcement.CreateAnnouncementReq;
import com.service.common.dto.request.announcement.CreateCompanyReq;
import com.service.common.dto.request.announcement.CreateCustomFormReq;
import com.service.announcement.service.AnnouncementService;
import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadAnnouncementRes;
import com.service.common.dto.response.announcement.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final CloudFileUpload cloudFileUpload;

    // 채용담당자 공고 등록1 (공고 등록), 상세(selectForm 0), 간단정보 및 이미지(selectForm 1)
    @PostMapping("/create/step1")
    public ResponseEntity<BaseResponse<CreateAnnouncementRes>> createStep1(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestPart CreateAnnouncementReq dto,
        @RequestPart(required = false) MultipartFile file) throws BaseException {
        String fileUrl = cloudFileUpload.upload(file);
        CreateAnnouncementRes response = announcementService.createStep1(memberIdx, memberRole, fileUrl, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_SUCCESS, response));
    }

    // 채용담당자 공고 등록2 (지원서폼등록)
    @PostMapping("/create/step2")
    public ResponseEntity<BaseResponse<CreateCustomFormRes>> createStep2(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestBody CreateCustomFormReq dto) throws BaseException {
        CreateCustomFormRes response = announcementService.createStep2(memberIdx, memberRole, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_SUCCESS, response));
    }

    // 지원서, 자기소개서 폼 조회
    @GetMapping("/read/form")
    public ResponseEntity<ReadCustomFormRes> readForm(
        @RequestParam Long announcementIdx) throws BaseException {
        ReadCustomFormRes response = announcementService.readForm(announcementIdx);
        return ResponseEntity.ok(response);
    }

    // 공고 상세 조회 (메인 지원자 페이지에서 공고 상세)
    @GetMapping("/read")
    public ResponseEntity<BaseResponse<ReadAnnouncementRes>> read(
        @RequestParam Long announcementIdx) throws BaseException {
        ReadAnnouncementRes response = announcementService.read(announcementIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_READ_SUCCESS, response));
    }
    // 공고 상세 조회 (메인 지원자 페이지에서 공고 상세 내부)
    @GetMapping("/read/internal")
    public ResponseEntity<ReadAnnouncementRes> readInternal(
        @RequestParam Long announcementIdx) throws BaseException {
        ReadAnnouncementRes response = announcementService.readInternal(announcementIdx);
        return ResponseEntity.ok(response);
    }

    // 필터 기반 상세 검색
//    @GetMapping("/search/filter")
//    public ResponseEntity<BaseResponse<Read1AnnouncementRes>> search(
//        @RequestBody SearchAnnouncementReq dto) throws BaseException {
//        Page<Read1AnnouncementRes> response = announcementService.search(dto);
//        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_SEARCH_SUCCESS, response));
//    }

    // 공고 전체 목록 조회 (메인 지원자 페이지에서)
    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<Read1AnnouncementRes>> readAll() throws BaseException {
        List<Read1AnnouncementRes> response = announcementService.readAll1();
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_READ_SUCCESS, response));
    }

    // (채용담당자) 등록한 공고 목록 조회(공고관리, 지원서관리)
    @GetMapping("/read-all2")
    public ResponseEntity<BaseResponse<Read1AnnouncementRes>> readAll2(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestParam Integer page,
        @RequestParam Integer size) throws BaseException {
        Page<Read2AnnouncementRes> response = announcementService.readAll2(memberIdx, page, size);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_READ_ALL_SUCCESS, response));
    }

    // TODO: 인터뷰 일정 리스트를 조회할때 공고의 idx로 조회함 그때 openFEIGN으로 read로 announcement가 져와서 처리 ㅇㅇ
    // 채용담당자: 인터뷰 일정 목록 조회시 나오는 공고 목록(신입, 경력)
    @GetMapping("/read-all3")
    public ResponseEntity<BaseResponse<Read1AnnouncementRes>> readAll3(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam String careerBase,
        @RequestParam Integer pageNum) throws BaseException {
        List<Read3AnnouncementRes> response = announcementService.readAll3(memberIdx, memberEmail, memberRole, careerBase, pageNum);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_READ_ALL_SUCCESS, response));
    }

    // 페이징 처리용 전체 사이즈 조회
    @GetMapping("/read/size")
    public ResponseEntity<BaseResponse<?>> readSize(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestParam String careerBase) throws BaseException {
        Integer response = announcementService.readSize(memberIdx, careerBase);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_READ_ALL_SUCCESS, response));
    }

    // 기업 정보 등록
    @PostMapping("/create/company")
    public ResponseEntity<BaseResponse<CreateCompanyRes>> createCompany(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestPart CreateCompanyReq dto,
        @RequestPart(required = false) MultipartFile[] files) throws BaseException {
        List<String> fileUrls = cloudFileUpload.multipleUpload(files);
        CreateCompanyRes response = announcementService.createCompany(memberIdx, memberEmail, memberRole,fileUrls, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_REGISTER_SUCCESS, response));
    }

    // 채용담당자 기업정보 조회 (마이페이지 입장 시)
    @GetMapping("/read/company")
    public ResponseEntity<BaseResponse<ReadCompanyRes>> readCompany(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole) throws BaseException {
        ReadCompanyRes response = announcementService.readCompany(memberIdx, memberEmail, memberRole);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_READ_INFO, response));
    }

    // 채용담당자 기업 복리후생 조회 (공고 등록을 위한 조회2, 공고 등록 페이지 클릭시)
    @GetMapping("/read/company-benefit")
    public ResponseEntity<BaseResponse<ReadCompanyBenefitRes>> readCompanyBenefit(
        @RequestHeader("X-User-Idx") Long memberIdx ) throws BaseException {
        ReadCompanyBenefitRes response = announcementService.readCompanyBenefit(memberIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_READ_INFO, response));
    }

    // 기업 이미지 조회
    @GetMapping("/read/company-img")
    public ResponseEntity<BaseResponse<ReadCompanyRes>> readCompanyImg(
        @RequestParam Long companyIdx) throws BaseException {
        ReadCompanyRes response = announcementService.readCompanyImg(companyIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.COMPANY_READ_INFO, response));
    }
}