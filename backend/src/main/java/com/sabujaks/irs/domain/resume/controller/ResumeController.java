package com.sabujaks.irs.domain.resume.controller;

import com.sabujaks.irs.domain.resume.model.request.ResumeCreateReq;
import com.sabujaks.irs.domain.resume.model.request.ResumeSubmitReq;
import com.sabujaks.irs.domain.resume.model.response.ResumeCreateRes;
import com.sabujaks.irs.domain.resume.service.ResumeService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.CloudFileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final CloudFileUpload cloudFileUpload;

    // 마이페이지 -> 지원서 등록
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ResumeCreateReq>> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart ResumeCreateReq dto,
            @RequestPart MultipartFile file,
            @RequestPart(required    = false) MultipartFile[] portfolioFiles) throws BaseException {
        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
        Long seekerIdx = customUserDetails.getIdx();

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
        ResumeCreateRes response = resumeService.create(seekerIdx, dto, fileUrl);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_REGISTER_SUCCESS, response));
    }
    
    // 공고 -> 지원서 등록
    @PostMapping("/submit")
    public ResponseEntity<BaseResponse<ResumeCreateReq>> submit(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart ResumeSubmitReq dto,
            @RequestPart MultipartFile file) throws BaseException {
        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
        Long seekerIdx = customUserDetails.getIdx();

        if(file.isEmpty()) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_FILE);
        }

        String fileUrl = cloudFileUpload.upload(file);
        ResumeCreateRes response = resumeService.submit(seekerIdx, dto, fileUrl);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_REGISTER_SUCCESS, response));
    }
}
