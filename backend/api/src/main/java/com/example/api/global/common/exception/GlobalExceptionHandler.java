package com.example.api.global.common.exception;

import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<BaseResponse> handleIOException(IOException e) {
//        BaseResponse<String> baseResponse = new BaseResponse<>(BaseResponseMessage.UNPARSE_JSON, e.getMessage());
//        return ResponseEntity.badRequest().body(baseResponse);
//    }

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
    public ResponseEntity<BaseResponse<String>> handleAuthenticationException(AuthenticationException e) {
        if (e instanceof BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(BaseResponseMessage.BAD_CREDENTIAL, e.getMessage()));
        } else if (e instanceof InternalAuthenticationServiceException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(BaseResponseMessage.USER_NOT_FOUND, e.getMessage()));
        } else if (e instanceof InsufficientAuthenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(BaseResponseMessage.USER_NOT_FOUND, e.getMessage()));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(BaseResponseMessage.INVALID_TOKEN, e.getMessage()));
        }
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse<String>> handleJwtException(JwtException e) {
        if(e instanceof ExpiredJwtException){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(BaseResponseMessage.JWT_TOKEN_EXPIRED, e.getMessage()));
        } else if(e instanceof UnsupportedJwtException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(BaseResponseMessage.JWT_TOKEN_UNSUPPORTED, e.getMessage()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse<String>> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BaseResponse(BaseResponseMessage.ACCESS_DENIED));
    }

}
