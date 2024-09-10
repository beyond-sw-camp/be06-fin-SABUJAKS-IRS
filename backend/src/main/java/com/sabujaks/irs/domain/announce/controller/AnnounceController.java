package com.sabujaks.irs.domain.announce.controller;

import com.sabujaks.irs.domain.announce.model.entity.CustomForm;
import com.sabujaks.irs.domain.announce.model.request.CustomFormReq;
import com.sabujaks.irs.domain.announce.model.response.CustomFormRes;
import com.sabujaks.irs.domain.announce.service.AnnounceService;
import com.sabujaks.irs.domain.auth.model.request.AuthSignupReq;
import com.sabujaks.irs.domain.resume.model.request.ResumeCreateReq;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announce")
@RequiredArgsConstructor
public class AnnounceController {
    private final AnnounceService announceService;

    @RequestMapping(method = RequestMethod.POST, value = "/register-step2")
    public ResponseEntity<BaseResponse<CustomFormReq>> registerStepTwo(
            @RequestBody CustomFormReq dto) throws BaseException {
        //공고를 작성하고 step2로 넘어오면 -> 채용담당자(기본값)?, 공고idx가 넘어와야 함
        //@AuthenticationPrincipal UserDetails userDetails 매개변수에 추가하기 (추후 수정 예정)
//        if (customUserDetails == null) throw new BaseException(BaseResponseMessage.AUTH_FAIL);
//        Long recruiterIdx = customUserDetails.getIdx();

        //채용담당자도 넘길 필요가 있는지 고민
        CustomFormRes response = announceService.registerCustomForm(dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_SUCCESS, response));
    }
}
