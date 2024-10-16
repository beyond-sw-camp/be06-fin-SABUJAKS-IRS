package com.service.verification.controller;

import com.service.common.dto.feign.ValidateCrnApiRes;
import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadVerificationRes;
import com.service.common.dto.request.verification.VerifyCompanyReq;
import com.service.common.dto.response.verification.VerifyCompanyRes;
import com.service.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    // 이메일 인증
    @GetMapping("/verify/email")
    public ResponseEntity<BaseResponse> verifyEmail(
        @RequestParam String email,
        @RequestParam String role,
        @RequestParam String uuid) throws BaseException {
        verificationService.verifyEmail(email, role, uuid);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_EMAIL_VERIFY_SUCCESS));
    }

    // 기업 인증
    @PostMapping("/verify/company")
    public ResponseEntity<BaseResponse<ValidateCrnApiRes>> verifyCompany(
        @RequestBody VerifyCompanyReq dto) throws BaseException {
        VerifyCompanyRes response = verificationService.verifyCompany(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_COMPANY_VERIFY_SUCCESS, response));
    }

    // 인증 값 조회
    @GetMapping("/read")
    public ResponseEntity<ReadVerificationRes> readVerification(
        @RequestParam String email,
        @RequestParam Boolean flag) throws BaseException {
        ReadVerificationRes response = verificationService.readVerification(email, flag);
        return ResponseEntity.ok(response);
    }
}
