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
    EMAIL_SEND_FAIL(false, 311, "이메일 전송에 실패했습니다."),
    INTERNAL_SERVER_ERROR(false, 500, "내부 서버 오류가 발생해서 처리할 수 없습니다."),

    // AUTH 1000~1999
    // 회원가입 1000
    AUTH_REGISTER_SUCCESS(true, 1000, "회원가입에 성공했습니다. 이메일 인증 후 로그인 해주세요"),
    AUTH_REGISTER_FAIL_USER_ALREADY_EXITS(false, 1001, "이미 다른 계정이 존재합니다." ),
    AUTH_REGISTER_FAIL_INVALID_ROLE(false, 1002, "유효하지 않은 회원가입입니다. "),
    AUTH_REGISTER_FAIL_NOT_COMPANY_AUTH(false, 1003, "기업 인증을 하지 않은 사용자는 가입할 수 없습니다."),
    AUTH_REGISTER_FAIL_ALREADY_REGISTER_AS_SEEKER(false, 1004, "이미 지원자 회원으로 가입된 계정입니다. 지원자 회원은 채용담당자 회원으로 회원가입 할 수 없습니다."),
    AUTH_REGISTER_FAIL_ALREADY_REGISTER_AS_RECRUITER(false, 1004, "이미 채용담당자 회원으로 가입된 계정입니다. 채용담당자 회원은 지원자 회원으로 회원가입 할 수 없습니다."),
    AUTH_RESTORE_SUCCESS(true, 1010, "비활성화된 계정입니다. 복구 절차로 이메일 인증을 해주세요"),
    // 이메일 검증 1100
    AUTH_EMAIL_VERIFY_SUCCESS(true, 1100, "이메일 검증을 완료했습니다."),
    AUTH_EMAIL_VERIFY_FAIL(false, 1101, "이메일 검증을 실패했습니다."),
    AUTH_EMAIL_VERIFY_FAIL_NOT_FOUND(false, 1102, "해당 유저를 찾을 수 없습니다."),
    AUTH_EMAIL_VERIFY_FAIL_INVALID_ROLE(false, 1103, "유효하지 않은 이메일 검증입니다."),

    // 기업 인증 1200
    AUTH_COMPANY_VERIFY_SUCCESS(true, 1200, "기업 인증을 완료했습니다."),
    AUTH_COMPANY_VERIFY_FAIL(false, 1201, "기업 인증에 실패했습니다."),
    AUTH_COMPANY_VERIFY_FAIL_INVALID_REQUEST(false, 1202, "유효하지 않은 요청입니다."),

    // 회원 정보 조회 1300
    AUTH_SEARCH_USER_INFO_SUCCESS(true, 1310, "회원 정보 조회에 성공했습니다."),
    AUTH_SEARCH_USER_INFO_FAIL(false, 1311, "회원 정보 조회에 실패했습니다."),

    // 비밀 번호 변경 1400
    AUTH_EDIT_PASSWORD_SUCCESS(true, 1400, "계정 비밀번호 변경에 성공했습니다."),
    AUTH_EDIT_PASSWORD_FAIL_PASSWORD_NOT_MATCH(false, 1401, "계정 패스워드가 다릅니다."),
    AUTH_EDIT_PASSWORD_FAIL(false, 1402, "계정 비밀번호 변경에 실패했습니다."),

    // 계정 비활성화 1500
    AUTH_INACTIVE_USER_SUCCESS(true, 1500, "계정 비활성화에 성공했습니다."),
    AUTH_INACTIVE_USER_FAIL(false, 1501, "계정 비활성화에 실패했습니다."),

    // INTERVIEW_SCHEDULE 면접 일정 1300
    INTERVIEW_SCHEDULE_CREATE_SUCCESS(true, 1300, "면접 일정 등록에 성공했습니다."),
    RESCHEDULE_CREATE_SUCCESS(true, 1301, "면접 조율 신청 생성 완료"),
    INTERVIEW_SCHEDULE_NOT_FOUND(false, 1302, "해당 면접을 찾을 수 없습니다."),
    RESCHEDULE_NOT_FOUND(false, 1303, "면접 조율 내역을 찾을 수 없습니다."),
    RESCHEDULE_SEARCH_ALL_SUCCESS(true, 1304, "면접 조율 내역 조회에 성공했습니다."),
    INTERVIEW_SCHEDULE_READ_ALL_SUCCESS(true, 1305, "면접 내역 조회에 성공했습니다."),
    INTERVIEW_SCHEDULE_UPDATE_SUCCESS(true, 1306, "면접 내역 업데이트에 성공했습니다."),

    INTERVIEW_PARTICIPATE_NOT_FOUND(false, 1320, "면접 연관 정보를 찾을 수 없습니다."),

    // VIDEO_INTERVIEW 화상 면접 1400
    VIDEO_INTERVIEW_CREATE_SUCCESS(true, 1400, "화상 면접 방 생성에 성공했습니다."),
    VIDEO_INTERVIEW_SEARCH_ALL_SUCCESS(true, 1401, "화상 면접방 목록 조회에 성공했습니다."),
    VIDEO_INTERVIEW_SEARCH_ALL_FAIL(false, 1402, "화상 면접방 목록 조회에 실패했습니다."),
    VIDEO_INTERVIEW_JOIN_SUCCESS(true, 1403, "화상 면접방에 참가했습니다."),
    VIDEO_INTERVIEW_JOIN_FAIL(false, 1404, "화상 면접방 참가에 실패했습니다."),

    // MEMBER 1500
    MEMBER_NOT_FOUND(false, 1500, "회원을 찾을 수 없습니다."),

    // TEAM 1600
    TEAM_NOT_FOUND(false, 1600, "팀을 찾을 수 없습니다."),

    // RESUME 2000~2999
    RESUME_REGISTER_SUCCESS(true, 2000, "지원서 등록에 성공했습니다."),
    RESUME_REGISTER_FAIL(false, 2001, "지원서 등록에 실패하였습니다." ),
    RESUME_REGISTER_FAIL_NOT_FOUND_ANNOUNCE(false, 2002, "해당 공고를 찾을 수 없습니다."),
    RESUME_REGISTER_FAIL_INTEGRATED(false, 2003, "통합 지원서가 이미 존재합니다."),
    RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER(false, 2004, "해당 유저를 찾을 수 없습니다."),
    RESUME_REGISTER_FAIL_NOT_FOUND_FILE(false, 2005, "파일을 찾을 수 없습니다."),
    RESUME_REGISTER_FAIL_NOT_FOUND_CUSTOM_FORM(false, 2006, "지원서 맞춤 양식을 조회할 수 없습니다."),
    RESUME_REGISTER_FAIL_RESUME_DUPLICATE(false, 2007, "이미 지원서가 제출된 공고입니다."),
    RESUME_READ_SUCCESS(true, 2100, "지원서 조회에 성공하였습니다." ),
    RESUME_READ_FAIL(false, 2101, "지원서 조회에 실패하였습니다." ),
    RESUME_READ_FAIL_INTEGRATED(false, 2102, "통합 지원서를 등록하지 않았습니다. 먼저 등록해주세요." ),
    RESUME_READ_FAIL_RESUME(false, 2103, "해당 공고에 지원한 기록이 존재하지 않습니다." ),
    RESUME_READ_FAIL_RESUME_INFO(false, 2104, "해당 공고에 지원한 지원서가 존재하지 않습니다." ),
    RESUME_UPDATE_SUCCESS(true, 2200, "지원서 수정에 성공하였습니다." ),
    RESUME_UPDATE_SUCCESS_DOC_PASSED(true, 2201, "서류 합격/불합격 처리 완료되었습니다." ),
    RESUME_UPDATE_FAIL_DOC_PASSED(false, 2202, "서류 합격/불합격 처리에 실패하였습니다." ),
    RESUME_UPDATE_FAIL_DOC_PASSED_ALREADY(false, 2202, "이미 처리되었습니다." ),


    // ANNOUNCEMENT 3000~3999
    ANNOUNCEMENT_REGISTER_STEP_ONE_SUCCESS(true, 3000, "공고 등록에 성공했습니다."),
    ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER(false, 3001, "채용담당자 유저가 아닙니다."),
    ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL(false, 3002, "공고 등록에 실패했습니다."),
    ANNOUNCEMENT_REGISTER_STEP_TWO_SUCCESS(true, 3100, "지원서 폼 조립에 성공했습니다."),
    ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_NOT_FOUND(false, 3101, "공고가 저장되지 않아 찾을 수 없습니다."),
    ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_ALREADY(false, 3102, "이미 지원서 폼이 저장된 공고입니다."),
    ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL(false, 3103, "지원서 폼 조립에 실패하였습니다." ),
    ANNOUNCEMENT_READ_ALL_SUCCESS(true, 3200, "공고 전체조회에 성공했습니다."),
    ANNOUNCEMENT_SEARCH_SUCCESS(true, 3300, "공고 조회에 성공했습니다."),
    ANNOUNCEMENT_SEARCH_FAIL(false, 3301, "공고 조회에 실패했습니다."),
    ANNOUNCEMENT_SEARCH_FAIL_NOT_FOUND(false, 3302, "해당 공고를 찾을 수 없습니다."),
    // COMPANY 기업정보 관련 4000~4999
    COMPANY_INFO_SUCCESS(true, 4000, "기업 정보 등록에 성공했습니다."),
    COMPANY_INFO_SUCCESS_REGISTER(true, 4001, "기업 복리후생 조회에 성공했습니다."),
    COMPANY_INFO_FAIL_NOT_RECRUITER(false, 4002, "채용담당자 유저가 아닙니다."),
    COMPANY_INFO_FAIL(false, 4003, "기업 정보 등록에 실패했습니다."),
    COMPANY_INFO_FAIL_NOT_REGISTER(false, 4004, "기업 정보를 등록하지 않았습니다. 먼저 등록해 주세요."),

    // ALARM 5000~5999
    ALARM_REGISTER_SUCCESS(true, 5000, "알람 생성이 완료되었습니다."),
    ALARM_SEARCH_SUCCESS(true, 5001, "알람 조회에 성공했습니다."),
    ALARM_SEARCH_FAIL(false, 5002, "알람 조회에 실패했습니다."),

    // 인터뷰 평가 6000~6999
    // 인터뷰 평가표 생성 6000
    INTERVIEW_EVALUATE_CREATE_FORM_SUCCESS(true, 6000, "인터뷰 평가표 생성에 성공했습니다."),
    INTERVIEW_EVALUATE_CREATE_FORM_FAIL_ALREADY_EXIST(false, 6001, "인터뷰 평가표가 이미 존재합니다."),
    INTERVIEW_EVALUATE_CREATE_FORM_FAIL_INVALID_REQUEST(false, 6002, "해당 공고의 채용담당자가 아니면 인터뷰 평가표를 작성할 권한이 없습니다."),
    // 인터뷰 평가표 조회 6100
    INTERVIEW_EVALUATE_SEARCH_FORM_SUCCESS(true, 6100, "인터뷰 평가표 조회에 성공했습니다."),
    INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_IS_NOT_EXIST(false, 6101, "해당 공고의 인터뷰 평가표가 존재하지 않습니다."),
    INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS(false, 6102, "해당 공고의 인터뷰 평가표에 접근할 수 없습니다."),
    // 인터뷰 지원서 조회 6200
    INTERVIEW_EVALUATE_SEARCH_RESUME_SUCCESS(true, 6200, "지원자들의 지원서 정보를 불러오는데 성공했습니다." ),
    INTERVIEW_EVALUATE_SEARCH_RESUME_FAIL_NOT_FOUND(false, 6201, "지원자들의 지원서 정보를 불러오는데 실패했습니다."),
    // 인터뷰 평가 생성 6300
    INTERVIEW_EVALUATE_CREATE_SUCCESS(true, 6300, "해당 지원자의 인터뷰 평가를 생성하는데 성공했습니다."),
    INTERVIEW_EVALUATE_CREATE_FAIL_INVALID_FORM(false, 6302, "해당 공고의 인터뷰 평가 항목이 잘못되었습니다."),
    INTERVIEW_EVALUATE_CREATE_FAIL_NOT_FOUND_SCHEDULE(false, 6301, "해당 면접관과 지원자의 인터뷰 스케줄 정보가 없습니다.");

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
