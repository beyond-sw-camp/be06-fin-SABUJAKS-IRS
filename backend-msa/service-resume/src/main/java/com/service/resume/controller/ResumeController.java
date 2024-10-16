package com.service.resume.controller;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadSubmissionResumeRes;
import com.service.common.dto.response.resume.*;
import com.service.resume.config.CloudFileUpload;
import com.service.common.dto.request.resume.CreateResumeReq;
import com.service.common.dto.request.resume.SubmitResumeReq;
import com.service.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final CloudFileUpload cloudFileUpload;
//    private final EmailSenderSeeker emailSenderSeeker;
//    private final EmailService emailService;

    // (지원자) 마이페이지 -> 통합 지원서 등록
    @PostMapping("/create/integrated")
    public ResponseEntity<BaseResponse<CreateIntegratedResumeRes>> createIntegrated(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestPart CreateResumeReq dto,
        @RequestPart(required = false) MultipartFile file,
        @RequestPart(required = false) MultipartFile[] portfolioFiles) throws BaseException {
        String fileUrl = cloudFileUpload.createResumeAttachmentsUpload(dto, portfolioFiles, file);
        CreateIntegratedResumeRes response = resumeService.createIntegrated(memberIdx, memberRole, dto, fileUrl);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_REGISTER_SUCCESS, response));
    }

    // (지원자) 공고 -> 지원서 등록
    @PostMapping("/create/submission")
    public ResponseEntity<BaseResponse<CreateSubmissionResumeRes>> createSubmission(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestPart SubmitResumeReq dto,
        @RequestPart MultipartFile file,
        @RequestPart(required = false) MultipartFile[] portfolioFiles) throws BaseException {
        String fileUrl = cloudFileUpload.submitResumeAttachmentsUpload(dto, portfolioFiles, file);
        CreateSubmissionResumeRes response = resumeService.createSubmission(memberIdx, memberRole, dto, fileUrl);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_SUBMIT_SUCCESS, response));
    }

    // (지원자) 공고 -> 지원서 제출 페이지 진입 시 커스텀 폼, 자기소개서 커스텀 폼, 통합지원서 조회
    @GetMapping("/enter/submission")
    public ResponseEntity<BaseResponse<EnterResumeRes>> enterSubmission(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam Long announcementIdx) throws BaseException {
        EnterResumeRes response = resumeService.enterSubmission(memberIdx, memberEmail, memberRole, announcementIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }

    // (지원자) 마이페이지 -> 통합 지원서 조회
    @GetMapping("/read/integrated")
    public ResponseEntity<BaseResponse<ReadIntegratedResumeRes>> readIntegrated(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Role") String memberRole) throws BaseException {
        ReadIntegratedResumeRes response = resumeService.readIntegrated(memberIdx, memberRole);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }

    // (지원자, 채용담당자, 면접자) 지원서 상세 조회
    @GetMapping("/read/submission")
    public ResponseEntity<BaseResponse<ReadSubmissionResumeRes>> readSubmission(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestParam (required = false) Long seekerIdx,
        @RequestParam (required = false) Long announcementIdx,
        @RequestParam (required = false) Long submissionResumeIdx) throws BaseException {
        ReadSubmissionResumeRes response = resumeService.readSubmission(memberIdx, memberEmail, memberRole, submissionResumeIdx, seekerIdx, announcementIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }


//    // (지원자) 공고별 지원서 관리 목록
    @GetMapping("/read-all/se/submission")
    public ResponseEntity<BaseResponse<ReadAllSeResumeRes>> readAllSeSubmission(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam Integer page,
        @RequestParam Integer size) throws BaseException {
        Page<ReadAllSeResumeRes> response = resumeService.readAllSeSubmission(memberIdx, memberEmail, memberRole, page, size);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }

    // (채용담당자) 공고에 지원한 지원자 목록 페이징 처리
    @GetMapping("/read-all/re/submission")
    public ResponseEntity<BaseResponse<ReadAllReResumeRes>> readAllReSubmission(
            @RequestHeader("X-User-Idx") Long memberIdx,
            @RequestHeader("X-User-Email") String memberEmail,
            @RequestHeader("X-User-Role") String memberRole,
            @RequestParam Long announcementIdx,
            @RequestParam Integer page,
            @RequestParam Integer size) throws BaseException {
        Page<ReadAllReResumeRes> response = resumeService.readAllReSubmission(memberIdx, memberEmail, memberRole, announcementIdx, page, size);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS_RESUMED_MEMBER, response));
    }
//      // TODO: 배치?
//    // (채용담당자) 공고에 지원한 지원자 서류 결과 전송
//    @PostMapping("/recruiter/send-result")
//    public ResponseEntity<BaseResponse<?>> sendResult(
//            @AuthenticationPrincipal CustomUserDetails customUserDetails,
//            @RequestBody List<ReadAllReResumeRes> resumeList) throws BaseException {
//
//        List<ResumeResultRes> resultInfo = emailService.getInfo(customUserDetails, resumeList);
//        emailSenderSeeker.sendResumeResultEmail(resultInfo);
//        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS_RESUMED_MEMBER));
//    }

}
