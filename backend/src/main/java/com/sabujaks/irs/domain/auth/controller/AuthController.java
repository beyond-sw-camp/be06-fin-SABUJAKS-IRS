package com.sabujaks.irs.domain.auth.controller;

import com.sabujaks.irs.domain.auth.model.response.RecruiterInfoReadRes;
import com.sabujaks.irs.domain.auth.model.request.AuthSignupReq;
import com.sabujaks.irs.domain.auth.model.request.CompanyVerifyReq;
import com.sabujaks.irs.domain.auth.model.request.PasswordEditReq;
import com.sabujaks.irs.domain.auth.model.response.*;
import com.sabujaks.irs.domain.auth.service.AuthService;
import com.sabujaks.irs.domain.auth.service.CompanyVerifyService;
import com.sabujaks.irs.domain.auth.service.EmailVerifyService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.CloudFileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailVerifyService emailVerifyService;
    private final CompanyVerifyService crnVerifyService;
    private final CloudFileUpload cloudFileUpload;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<AuthSignupRes>> signup (
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart("dto") AuthSignupReq dto) throws BaseException {
        String fileUrl = cloudFileUpload.upload(file);
        AuthSignupRes response = authService.signup(dto, fileUrl);
        String uuid = emailVerifyService.sendMail(response);
        emailVerifyService.save(dto.getEmail(), uuid);
        if(response.getInactive()){
            return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_RESTORE_SUCCESS, response));
        }else{
            return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_REGISTER_SUCCESS, response));
        }
    }

    @GetMapping("/email-verify")
    public ResponseEntity<Void> emailVerify(
            String email, String role, String uuid) throws Exception, BaseException {
        HttpHeaders headers = new HttpHeaders();

        if (emailVerifyService.isExist(email, uuid)) {
            authService.activeUser(email, role);
            headers.setLocation(URI.create("https://www.sabujaks-irs/seeker/login"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            headers.setLocation(URI.create("https://www.sabujaks-irs/seeker/login?error=true"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }

    @PostMapping("/company-verify")
    public ResponseEntity<BaseResponse<CrnApiRes>> companyVerify(
        @RequestBody CompanyVerifyReq dto) throws BaseException {
        CompanyVerifyRes response = crnVerifyService.companyVerify(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_COMPANY_VERIFY_SUCCESS, response));
    }

    @GetMapping("/user-info")
    public ResponseEntity<BaseResponse<UserInfoGetRes>> userInfo(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException {
        UserInfoGetRes response = authService.userInfo(customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_SEARCH_USER_INFO_SUCCESS, response));
    }

    @PostMapping("/edit-password")
    public ResponseEntity<BaseResponse> editPassword(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody PasswordEditReq dto) throws BaseException {
        authService.editPassword(customUserDetails, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_EDIT_PASSWORD_SUCCESS));
    }

    @GetMapping("/inactive-user")
    public ResponseEntity<BaseResponse> inactiveUser(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException {
        authService.inactiveUser(customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_INACTIVE_USER_SUCCESS));
    }


    // (지원자) 회원정보 조회
    @GetMapping("/seeker/read")
    public ResponseEntity<BaseResponse<SeekerRes>> readSeeker(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException {
        SeekerRes response = authService.readSeeker(customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_SEARCH_USER_INFO_SUCCESS, response));
    }

    // 채용담당자 일부 정보 조회 (공고 등록을 위한 조회1)
    @GetMapping("/read-recruiter-info")
    public ResponseEntity<BaseResponse<RecruiterInfoReadRes>> readRecruiterInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException {

        RecruiterInfoReadRes response = authService.readRecruiterInfo(customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_SEARCH_USER_INFO_SUCCESS, response));
    }
}
