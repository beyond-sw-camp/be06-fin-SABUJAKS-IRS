package com.sabujaks.irs.domain.resume.controller;

import com.sabujaks.irs.domain.resume.model.request.ResumeCreateReq;
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

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final CloudFileUpload cloudFileUpload;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ResumeCreateReq>> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart ResumeCreateReq dto,
            @RequestPart MultipartFile file) throws BaseException {
        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
        Long seekerIdx = customUserDetails.getIdx();

        if(file.isEmpty()) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_FILE);
        }

        String fileUrl = cloudFileUpload.upload(file);
        ResumeCreateRes response = resumeService.create(seekerIdx, dto, fileUrl);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESUME_REGISTER_SUCCESS, response));
    }
}
