package com.sabujaks.irs.domain.data_init;

import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import com.sabujaks.irs.domain.data_init.repository.BaseInfoRepository;
import com.sabujaks.irs.domain.interview_schedule.model.entity.Team;
import com.sabujaks.irs.domain.interview_schedule.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
//@Component
public class DataInit {
    private final BaseInfoRepository baseInfoRepository;
    private final TeamRepository teamRepository;

    // 어노테이션 주석 해제하면 init 됨
    // 테이블 없으면 yml create로 바꾸고 주석해제 후 프로젝트 실행(한번 넣기)
    // 그 후 주석처리 안하고 프로젝트 돌리면 중복으로 들어가니까 다시 주석처리!
//    @PostConstruct //애플리케이션의 빈이 초기화될 때 실행
    public void dataInsert() {

        // 1. 지원서 폼 등록
        String groupName1 = "custom_form";
        String[] descriptions1 = {
                "학력", "경력", "인턴&대외활동", "교육이수", "자격증",
                "수상", "해외경험", "어학", "포트폴리오", "취업우대&병역", "자기소개서"
        };
        for (int i = 0; i < descriptions1.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName1)
                    .code(String.format("resume_%03d", i + 1))  // resume_001 부터 순차 증가
                    .description(descriptions1[i])
                    .parentCode(null) // 최상위 그룹이므로 parentCode 없음
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 2. 복리후생 폼 등록
        String groupName2 = "benefits";
        String[] descriptions2 = {
                "연금&보험", "휴무&휴가&행사", "보상&수당&지원", "사내제도&성장", "편의&여가&건강",
                "근무환경"
        };
        for (int i = 0; i < descriptions2.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName2)
                    .code(String.format("benefits_%03d", i + 1))  // benefits_001 부터 순차 증가
                    .description(descriptions2[i])
                    .parentCode(null) // 최상위 그룹이므로 parentCode 없음
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 2-1. 복리후생 > 연금&보험 등록 (benefits_001 하위)
        String parentCode1 = "benefits_001";
        String groupName3 = "연금&보험";
        String[] descriptions3 = {
                "국민연금", "고용보험", "산재보험", "건강보험", "개인연금"
        };
        for (int i = 0; i < descriptions3.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName3)
                    .code(String.format("%s_%03d", parentCode1, i + 1))  // benefits_001_001 부터 순차 증가
                    .description(descriptions3[i])
                    .parentCode(parentCode1) // 상위 코드 설정
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 4. 복리후생 > 휴무&휴가&행사 등록 (benefits_002 하위)
        String parentCode2 = "benefits_002";
        String groupName4 = "휴무&휴가&행사";
        String[] descriptions4 = {
                "반차제도", "연차제도", "자유로운 휴가문화", "근로자의 날 휴무"
        };
        for (int i = 0; i < descriptions4.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName4)
                    .code(String.format("%s_%03d", parentCode2, i + 1))  // benefits_002_001 부터 순차 증가
                    .description(descriptions4[i])
                    .parentCode(parentCode2) // 상위 코드 설정
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 5. 복리후생 > 보상&수당&지원 등록 (benefits_003 하위)
        String parentCode3 = "benefits_003";
        String groupName5 = "보상&수당&지원";
        String[] descriptions5 = {
                "퇴직금", "경조금", "자녀학자금", "본인학자금",
                "교육비 지원", "자격증 취득 지원", "자기계발비 지원",
                "장기근속 포상", "우수사원 포상제도", "복지포인트",
                "연차수당", "휴일 수당", "성과급", "퇴직금", "인센티브제",
                "주차 지원", "창립일 선물", "웰컴키트", "명절선물&귀향비", "생일선물"
        };
        for (int i = 0; i < descriptions5.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName5)
                    .code(String.format("%s_%03d", parentCode3, i + 1))  // benefits_003_001 부터 순차 증가
                    .description(descriptions5[i])
                    .parentCode(parentCode3) // 상위 코드 설정
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 6. 복리후생 > 사내제도&성장 등록 (benefits_004 하위)
        String parentCode4 = "benefits_004";
        String groupName6 = "사내제도&성장";
        String[] descriptions6 = {
                "신입 사원 교육",
                "님/닉네임 호칭", "수평적 문화", "유연근무제", "자율복장",
                "사내동호회 운영",
        };
        for (int i = 0; i < descriptions6.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName6)
                    .code(String.format("%s_%03d", parentCode4, i + 1))  // benefits_004_001 부터 순차 증가
                    .description(descriptions6[i])
                    .parentCode(parentCode4) // 상위 코드 설정
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 7. 복리후생 > 편의&여가&건강 등록 (benefits_005 하위)
        String parentCode5 = "benefits_005";
        String groupName7 = "편의&여가&건강";
        String[] descriptions7 = {
                "출퇴근 셔틀버스", "건강검진"
        };
        for (int i = 0; i < descriptions7.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName7)
                    .code(String.format("%s_%03d", parentCode5, i + 1))  // benefits_005_001 부터 순차 증가
                    .description(descriptions7[i])
                    .parentCode(parentCode5) // 상위 코드 설정
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 8. 복리후생 > 근무환경 등록 (benefits_006 하위)
        String parentCode6 = "benefits_006";
        String groupName8 = "근무환경";
        String[] descriptions8 = {
                "사무용품 지급", "노트북 지원", "비상경보장치", "카페테리아", "공기청정기",
                "회의실", "헬스룸"
        };
        for (int i = 0; i < descriptions8.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName8)
                    .code(String.format("%s_%03d", parentCode6, i + 1))  // benefits_006_001 부터 순차 증가
                    .description(descriptions8[i])
                    .parentCode(parentCode6) // 상위 코드 설정
                    .build();
            baseInfoRepository.save(baseInfo);
        }

        // 팀 정보 추가
        String[] teams = {
                "1팀", "2팀", "3팀", "4팀", "5팀"
        };

        for(String team : teams) {
            teamRepository.save(Team.builder()
                    .teamName(team)
                    .build());
        }

    }
}
