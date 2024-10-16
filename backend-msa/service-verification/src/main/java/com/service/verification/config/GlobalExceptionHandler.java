package com.service.verification.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@Slf4j
@ControllerAdvice
@Component
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

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

    @ExceptionHandler
    public ResponseEntity<BaseResponse<String>> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BaseResponse(BaseResponseMessage.ACCESS_DENIED));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity feignExceptionHandler(FeignException e) throws JsonProcessingException {
        String responseJson = e.contentUTF8();
        Map<String, String> responseMap = objectMapper.readValue(responseJson, Map.class);

        return new ResponseEntity<>(responseMap, HttpStatus.valueOf(e.status()));
    }

}
