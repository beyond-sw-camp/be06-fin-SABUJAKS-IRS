package com.sabujaks.irs.global.common.exception;

import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MailException.class)
    public ResponseEntity<BaseResponse> handleMailException(MailException e){
        BaseResponse<String> baseResponse = new BaseResponse<>(BaseResponseMessage.EMAIL_SEND_FAIL, e.getMessage());
        return ResponseEntity.badRequest().body(baseResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<BaseResponse<String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        BaseResponse<String> baseResponse = new BaseResponse<>(BaseResponseMessage.DATABASE_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(baseResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        BaseResponse<String> response = new BaseResponse<>(BaseResponseMessage.DATABASE_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<String>> handleBaseException(BaseException e){
        return ResponseEntity.badRequest().body(new BaseResponse(BaseResponseMessage.findByCode(e.getCode())));
    }
}
