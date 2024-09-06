package com.sabujaks.irs.global.common.responses;

public enum BaseResponseMessage {
    REQUEST_SUCCESS(true, 200, "요청이 정상적으로 처리되었습니다"),
    REQUEST_FAIL(false, 404, "요청을 실패했습니다."),

    // 1000
    MEMBER_REGISTER_SUCCESS(true, 1000, "회원가입에 성공했습니다."),
    MEMBER_REGISTER_FAIL_MEMBER_ALREADY_EXITS(false, 1001, "이미 다른 계정이 존재합니다." ),
    MEMBER_REGISTER_FAIL_INVALID_ROLE(false, 1002, "유효하지 않은 회원가입입니다. ")
    ;

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
