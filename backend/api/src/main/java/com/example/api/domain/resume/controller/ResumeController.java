package com.example.api.domain.resume.controller;

import com.example.api.domain.resume.model.request.ResumeCreateReq;
import com.example.api.domain.resume.model.request.ResumeSubmitReq;
import com.example.api.domain.resume.model.response.*;
import com.example.api.domain.resume.service.ResumeService;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import com.example.api.global.utils.CloudFileUpload;
import com.example.api.global.utils.email.service.EmailService;
import com.example.api.global.utils.email.model.response.ResumeResultRes;
import com.example.api.global.utils.email.service.EmailSenderSeeker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final CloudFileUpload cloudFileUpload;
    private final EmailSenderSeeker emailSenderSeeker;
    private final EmailService emailService;

    // (지원자) 마이페이지 -> 지원서 등록
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ResumeCreateRes>> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart ResumeCreateReq dto,
            @RequestPart MultipartFile file,
            @RequestPart(required = false) MultipartFile[] portfolioFiles) throws BaseException {

        if(file.isEmpty()) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_FILE);
        }
        // 포트폴리오 파일 로직
        if(dto.getCodes().contains("resume_009")) {
            if (portfolioFiles != null && portfolioFiles.length > 0) {
                List<String> portfolioUrls = cloudFileUpload.multipleUpload(portfolioFiles);
                int fileIndex = 0;
                for (int i = 0; i < dto.getPortfolios().size(); i++) {
                    if (dto.getPortfolios().get(i).getPortfolioType().equals("파일")) {
                        // TYPE이 파일이고 URL이 null인 경우
                        if (fileIndex < portfolioFiles.length) {
                            dto.getPortfolios().get(i).setPortfolioUrl(portfolioUrls.get(fileIndex));
                            fileIndex++;
                        }
                    }
                }
            }
        }

        String fileUrl = cloudFileUpload.upload(file);
        ResumeCreateRes response = resumeService.create(customUserDetails, dto, fileUrl);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_REGISTER_SUCCESS, response));
    }

    // (지원자) 공고 -> 지원서 등록
    @PostMapping("/submit")
    public ResponseEntity<BaseResponse<ResumeSubmitRes>> submit(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart ResumeSubmitReq dto,
            @RequestPart MultipartFile file,
            @RequestPart(required = false) MultipartFile[] portfolioFiles) throws BaseException {

        if(file.isEmpty()) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_FILE);
        }

        // 포트폴리오 파일 로직
        if(dto.getCodes().contains("resume_009")) {
            if (portfolioFiles != null && portfolioFiles.length > 0) {
                List<String> portfolioUrls = cloudFileUpload.multipleUpload(portfolioFiles);
                int fileIndex = 0;
                for (int i = 0; i < dto.getPortfolios().size(); i++) {
                    if (dto.getPortfolios().get(i).getPortfolioType().equals("파일")) {
                        // TYPE이 파일이고 URL이 null인 경우
                        if (fileIndex < portfolioFiles.length) {
                            dto.getPortfolios().get(i).setPortfolioUrl(portfolioUrls.get(fileIndex));
                            fileIndex++;
                        }
                    }
                }
            }
        }

        String fileUrl = cloudFileUpload.upload(file);
        ResumeSubmitRes response = resumeService.submit(customUserDetails, dto, fileUrl);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_REGISTER_SUCCESS, response));
    }

    // (지원자) 공고 -> 지원서 제출 페이지 진입 시
    @GetMapping("/read/submit-info")
    public ResponseEntity<BaseResponse<ResumeReadSubmitInfoRes>> readSubmitInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Long announcementIdx) throws BaseException {

        ResumeReadSubmitInfoRes response = resumeService.readSubmitInfo(customUserDetails, announcementIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }

    // (지원자) 마이페이지 -> 통합 지원서 조회
    @GetMapping("/read/integrated")
    public ResponseEntity<BaseResponse<ResumeReadRes>> readIntegrated(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException {

        ResumeReadIntegratedRes response = resumeService.readIntegrated(customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }

    // (지원자, 채용담당자) 지원서 상세 조회
    @GetMapping("/read")
    public ResponseEntity<BaseResponse<ResumeReadRes>> read(Long resumeIdx) throws BaseException {

        ResumeReadRes response = resumeService.read(resumeIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }

    // (지원자) 공고별 지원서 관리 목록
    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<ResumeReadAllRes>> readAll(
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Integer page, Integer size) throws BaseException {

        Page<ResumeReadAllRes> response = resumeService.readAll(customUserDetails, page, size);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS, response));
    }

    // (채용담당자) 공고에 지원한 지원자 목록 페이징 처리
    @GetMapping("/recruiter/read-all")
    public ResponseEntity<BaseResponse<ResumeReadAllRes>> readAllRecruiter(
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Long announcementIdx, Integer page, Integer size) throws BaseException {

        Page<ResumeReadAllRecruiterRes> response = resumeService.readAllRecruiter(
                customUserDetails, announcementIdx, page, size
        );
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS_RESUMED, response));
    }

    // (채용담당자) 공고에 지원한 지원자 서류 결과 전송
    @PostMapping("/recruiter/send-result")
    public ResponseEntity<BaseResponse<?>> sendResult(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody List<ResumeReadAllRecruiterRes> resumeList) throws BaseException {

        List<ResumeResultRes> resultInfo = emailService.getInfo(customUserDetails, resumeList);
        emailSenderSeeker.sendResumeResultEmail(resultInfo);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_READ_SUCCESS_RESUMED));
    }

}
