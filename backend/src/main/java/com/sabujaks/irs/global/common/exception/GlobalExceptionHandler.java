package com.sabujaks.irs.global.common.exception;

import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<String>> handleBaseException(BaseException e){
        return ResponseEntity.badRequest().body(new BaseResponse(BaseResponseMessage.findByCode(e.getCode())));
    }

}
