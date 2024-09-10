package com.sabujaks.irs.global.common.responses;

public enum BaseResponseMessage {
    REQUEST_SUCCESS(true, 200, "요청이 정상적으로 처리되었습니다"),
    REQUEST_FAIL(false, 300, "요청을 실패했습니다."),
    DATABASE_SERVER_ERROR(false, 301, "DATABASE 오류"),
    INVALID_TOKEN(false, 302, "유효하지 않은 토큰입니다."),
    JWT_TOKEN_EXPIRED(false, 303, "JWT 토큰이 만료되었습니다."),
    JWT_TOKEN_UNSUPPORTED(false, 304, "JWT 토큰 형식이 아닙니다."),
    JWT_TOKEN_WRONG(false, 305, "JWT 토큰이 잘못됬습니다."),
    ACCESS_DENIED(false, 306, "접근이 거부되었습니다. 권한이 없습니다."),
    BAD_CREDENTIAL(false, 307, "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요"),
    USER_NOT_FOUND(false, 308, "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요."),
    FILE_UPLOAD_FAIL(false, 309,"이미지 업로드에 실패했습니다."),
    AUTH_FAIL(false, 310, "사용자 인증에 실패하였습니다."),


    INTERNAL_SERVER_ERROR(false, 500, "내부 서버 오류가 발생해서 처리할 수 없습니다."),
    // AUTH 1000~1999
    // MEMBER 회원가입 1000
    MEMBER_REGISTER_SUCCESS(true, 1000, "회원가입에 성공했습니다. 이메일 인증 후 로그인 해주세요"),
    MEMBER_REGISTER_FAIL_MEMBER_ALREADY_EXITS(false, 1001, "이미 다른 계정이 존재합니다." ),
    MEMBER_REGISTER_FAIL_INVALID_ROLE(false, 1002, "유효하지 않은 회원가입입니다. "),
    MEMBER_REGISTER_FAIL_NOT_COMPANY_AUTH(false, 1003, "기업 인증을 하지 않은 사용자는 가입할 수 없습니다."),

    // EMAIL_VERIFY 이메일 검증 1100
    EMAIL_VERIFY_SUCCESS(true, 1100, "이메일 검증을 완료했습니다."),
    EMAIL_VERIFY_FAIL(false, 1101, "이메일 검증을 실패했습니다."),
    EMAIL_VERIFY_FAIL_NOT_FOUND(false, 1102, "해당 유저를 찾을 수 없습니다."),
    EMAIL_VERIFY_FAIL_INVALID_ROLE(false, 1102, "유효하지 않은 이메일 검증입니다."),
    EMAIL_SEND_FAIL(false, 1103, "이메일 전송에 실패했습니다."),

    // COMPANY_VERIFY 기업 인증 1200
    COMPANY_VERIFY_SUCCESS(true, 1200, "기업 인증을 완료했습니다."),
    COMPANY_VERIFY_FAIL(false, 1201, "기업 인증에 실패했습니다."),
    COMPANY_VERIFY_FAIL_INVALID_REQUEST(false, 1202, "유효하지 않은 요청입니다."),

    // INTERVIEW_SCHEDULE 면접 일정 1300
    INTERVIEW_SCHEDULE_CREATE_SUCCESS(true, 1300, "면접 일정 등록에 성공했습니다."),
    // VIDEO_INTERVIEW 화상 면접 1400
    VIDEO_INTERVIEW_CREATE_SUCCESS(true, 1400, "화상 면접 방 생성에 성공했습니다."),
    VIDEO_INTERVIEW_SEARCH_FAIL_NOT_FOUND(false, 1401, "화상 면접방 목록 조회에 실패했습니다."),
    // RESUME 2000~2999
    RESUME_REGISTER_SUCCESS(true, 2000, "지원서 등록에 성공했습니다."),
    RESUME_REGISTER_FAIL_NOT_FOUND(false, 2001, "해당 유저를 찾을 수 없습니다."),
    RESUME_REGISTER_FAIL(false, 2002, "지원서 등록에 실패하였습니다." ),

    // ANNOUNCEMENT 3000~3999
    ANNOUNCEMENT_REGISTER_STEP_TWO_SUCCESS(true, 3000, "지원서 폼 조립에 성공했습니다."),
    ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_NOT_FOUND(false, 3001, "공고가 저장되지 않아 찾을 수 없습니다."),
    ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL(false, 3002, "지원서 폼 조립에 실패하였습니다." );

    private Boolean success;
    private Integer code;
    private String message;

    BaseResponseMessage(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public static BaseResponseMessage findByCode(Integer code) {
        for (BaseResponseMessage message : values()) { if (message.getCode().equals(code)) { return message; }}
        return null;
    }
    public Boolean getSuccess() { return success; }

    public Integer getCode() { return code; }

    public String getMessage() { return message; }
}
