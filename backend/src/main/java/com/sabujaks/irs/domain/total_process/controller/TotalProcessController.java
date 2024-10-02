package com.sabujaks.irs.domain.total_process.controller;

import com.sabujaks.irs.domain.total_process.model.request.TotalProcessCreateReq;
import com.sabujaks.irs.domain.total_process.model.response.TotalProcessCreateRes;
import com.sabujaks.irs.domain.total_process.service.TotalProcessService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/total-process")
public class TotalProcessController {
    private final TotalProcessService totalProcessService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<TotalProcessCreateRes>> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody TotalProcessCreateReq dto) throws BaseException {
        TotalProcessCreateRes response = totalProcessService.create(dto, customUserDetails);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.TOTAL_PROCESS_CREATE_SUCCESS, response));
    }

    @GetMapping("/ex01")
    public ResponseEntity<String> ex01(){
        return ResponseEntity.ok("ex0123");
    }
}
