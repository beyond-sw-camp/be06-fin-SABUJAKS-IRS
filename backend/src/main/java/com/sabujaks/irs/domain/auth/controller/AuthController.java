package com.sabujaks.irs.domain.auth.controller;

import com.sabujaks.irs.domain.auth.model.request.RecruiterSignupReq;
import com.sabujaks.irs.domain.auth.model.response.RecruiterSignupRes;
import com.sabujaks.irs.domain.auth.service.AuthService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/recruiter/signup")
    public ResponseEntity<BaseResponse<RecruiterSignupRes>> signup (
        @RequestBody RecruiterSignupReq dto) throws BaseException {
        RecruiterSignupRes response = authService.signup(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_SUCCESS, response));
    }
}
