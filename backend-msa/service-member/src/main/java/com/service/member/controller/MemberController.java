package com.service.member.controller;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadMemberRes;
import com.service.common.dto.request.member.EditPasswordReq;
import com.service.common.dto.request.member.SignupReq;
import com.service.common.dto.response.member.SignupRes;

import com.service.member.config.CloudFileUpload;
import com.service.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CloudFileUpload cloudFileUpload;
    // 회원가입 (API 테스트 O)
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignupRes>> signup (
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart("dto") SignupReq dto) throws BaseException {
        String fileUrl = cloudFileUpload.upload(file);
        SignupRes response = memberService.signup(dto, fileUrl);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_SIGNUP_SUCCESS, response));
    }

    // 계정 비활성화
    @GetMapping("/inactive")
    public ResponseEntity<BaseResponse> inActive(
            @RequestHeader("X-User-Idx") Long memberIdx,
            @RequestHeader("X-User-Role") String memberRole) throws BaseException {
        memberService.inActive(memberIdx, memberRole);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_INACTIVE_USER_SUCCESS));
    }

    // 비밀번호 변경
    @PostMapping("/edit/password")
    public ResponseEntity<BaseResponse> editPassword(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestBody EditPasswordReq dto) throws BaseException {
        memberService.editPassword(memberIdx, memberRole, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_EDIT_PASSWORD_SUCCESS));
    }

    // 유저 정보 조회
    @GetMapping("/read")
    public ResponseEntity<BaseResponse<ReadMemberRes>> read(
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam(required = false) String estimatorEmail,
        @RequestParam(required = false) Long seekerIdx) throws BaseException {
        ReadMemberRes response = memberService.read(memberEmail, memberRole, seekerIdx, estimatorEmail);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.AUTH_SEARCH_USER_INFO_SUCCESS, response));
    }
}
