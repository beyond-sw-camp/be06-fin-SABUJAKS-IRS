package com.service.announcement.service;

import com.service.announcement.entity.BaseInfo;
import com.service.announcement.repository.BaseInfoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DataInit {
    private final BaseInfoRepository baseInfoRepository;

    // 어노테이션 주석 해제하면 init 됨
    // 테이블 없으면 yml create로 바꾸고 주석해제 후 프로젝트 실행(한번 넣기)
    // 그 후 주석처리 안하고 프로젝트 돌리면 중복으로 들어가니까 다시 주석처리!
    @PostConstruct //애플리케이션의 빈이 초기화될 때 실행
    public void dataInsert() {
        if(baseInfoRepository.findById(1L).isEmpty()){
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
            String groupName9 = "팀";

            // 팀 정보 추가
            String[] description9 = {"1팀", "2팀", "3팀", "4팀", "5팀"};
            for(int i = 0; i < description9.length; i++){
                BaseInfo baseInfo = BaseInfo.builder()
                        .groupName(groupName9)
                        .code(String.format("%d%s", i+1, groupName9))
                        .description(description9[i])
                        .build();
                baseInfoRepository.save(baseInfo);
            }

            // 모집직무 job_category

            // 모집직무 카테고리 대분류 추가
            String groupName99 = "job_category";
            String[] descriptions = {
                    "기획&전략", "교육", "고객상담&TM", "IT&개발", "마케팅",
                    "영업", "인사", "재무&회계", "법무", "홍보&PR",
                    "디자인", "생산&제조", "연구개발(R&D)", "물류", "구매",
                    "건설&토목", "의료", "연구", "법무&특허", "서비스",
                    "비서&총무", "전략기획", "엔지니어", "유통&판매", "리스크관리",
                    "품질관리", "공공행정", "광고&미디어", "항공", "호텔&외식"
            };

            // 소분류 데이터
            Map<String, String[]> subCategories = new HashMap<>();
            subCategories.put("기획&전략", new String[]{"사업기획", "전략수립", "경영기획", "프로젝트관리", "사업분석", "시장조사", "리스크관리"});
            subCategories.put("교육", new String[]{"교육기획", "학습상담", "학원생관리", "교육운영", "교육컨설팅", "교육콘텐츠기획"});
            subCategories.put("고객상담&TM", new String[]{"고객상담", "TM상담", "해피콜", "클레임관리", "고객데이터관리", "서비스품질관리"});
            subCategories.put("IT&개발", new String[]{"프론트엔드개발", "백엔드개발", "모바일개발", "웹개발", "데이터베이스관리", "시스템엔지니어", "소프트웨어테스트"});
            subCategories.put("마케팅", new String[]{"디지털마케팅", "콘텐츠마케팅", "마케팅전략", "브랜드관리", "광고기획", "소셜미디어관리", "시장분석"});
            subCategories.put("영업", new String[]{"B2B영업", "B2C영업", "영업기획", "영업관리", "신규사업개발", "영업지원", "채널관리"});
            subCategories.put("인사", new String[]{"인사기획", "채용", "인재육성", "조직문화관리", "노무관리", "성과관리", "복리후생"});
            subCategories.put("재무&회계", new String[]{"재무관리", "회계", "세무", "재무기획", "자금관리", "리스크관리", "재무분석"});
            subCategories.put("법무", new String[]{"법무지원", "계약관리", "소송관리", "기업법무", "지적재산권관리", "컴플라이언스", "규제대응"});
            subCategories.put("홍보&PR", new String[]{"미디어관리", "언론대응", "홍보전략", "브랜드PR", "내부커뮤니케이션", "이벤트기획"});
            subCategories.put("디자인", new String[]{"그래픽디자인", "UI/UX디자인", "웹디자인", "광고디자인", "브랜드디자인", "제품디자인", "패키지디자인"});
            subCategories.put("생산&제조", new String[]{"생산관리", "공정관리", "설비관리", "품질관리", "자재관리", "안전관리", "생산계획"});
            subCategories.put("연구개발(R&D)", new String[]{"제품개발", "기술연구", "시장조사", "특허관리", "신제품기획", "기술기획", "기술동향분석"});
            subCategories.put("물류", new String[]{"물류관리", "재고관리", "유통기획", "운송관리", "창고관리", "국제물류", "SCM관리"});
            subCategories.put("구매", new String[]{"구매관리", "원가관리", "공급업체관리", "구매계약", "수입관리", "원자재구매"});
            subCategories.put("건설&토목", new String[]{"건축설계", "토목설계", "현장관리", "공사관리", "안전관리", "프로젝트관리", "자재관리"});
            subCategories.put("의료", new String[]{"간호", "진료지원", "의료기기관리", "환자관리", "의료코디네이터", "약사", "의무기록관리"});
            subCategories.put("연구", new String[]{"임상연구", "바이오연구", "의약품연구", "화학분석", "기초과학연구", "실험실관리", "기술연구"});
            subCategories.put("법무&특허", new String[]{"특허출원", "지식재산권관리", "법무상담", "계약관리", "소송대응", "컴플라이언스"});
            subCategories.put("서비스", new String[]{"고객서비스", "서비스기획", "리셉션", "클레임처리", "CS운영", "서비스교육"});
            subCategories.put("비서&총무", new String[]{"비서업무", "일정관리", "총무관리", "사무지원", "자산관리", "문서관리", "회의관리"});
            subCategories.put("전략기획", new String[]{"사업전략수립", "시장분석", "재무분석", "위험관리", "프로젝트관리", "경쟁사분석", "장기계획수립"});
            subCategories.put("엔지니어", new String[]{"기계설계", "전기설계", "시스템엔지니어", "자동화설비관리", "설비보전", "기술지원", "제품개발"});
            subCategories.put("유통&판매", new String[]{"판매관리", "매장관리", "상품기획", "유통기획", "재고관리", "프로모션기획", "매출분석"});
            subCategories.put("리스크관리", new String[]{"금융리스크관리", "재무리스크관리", "위험분석", "규제대응", "자산운용", "보안관리"});
            subCategories.put("품질관리", new String[]{"QC", "QA", "품질보증", "제품검사", "공정관리", "불량분석", "고객불만처리"});
            subCategories.put("공공행정", new String[]{"행정기획", "정책분석", "공공사업관리", "국제협력", "정책평가", "공공서비스개선"});
            subCategories.put("광고&미디어", new String[]{"광고기획", "미디어플래닝", "방송제작", "광고대행", "PR전략", "콘텐츠기획", "미디어구매"});
            subCategories.put("항공", new String[]{"항공기정비", "승무원", "공항운영", "화물운송", "비행기정비", "항공사운영"});
            subCategories.put("호텔&외식", new String[]{"호텔운영", "레스토랑운영", "조리", "객실관리", "외식기획", "행사기획", "프론트관리"});


            for (int i = 0; i < descriptions.length; i++) {
                String code = String.format("job_%03d", i + 1);
                String groupName88 = descriptions[i];

                // 대분류 저장
                BaseInfo baseInfo = BaseInfo.builder()
                        .groupName(groupName99)
                        .code(code)
                        .description(groupName88)
                        .parentCode(null)
                        .build();
                baseInfoRepository.save(baseInfo);

                // 소분류 추가
                String[] subCategoryDescriptions = subCategories.get(groupName88);
                if (subCategoryDescriptions != null) {
                    for (int j = 0; j < subCategoryDescriptions.length; j++) {
                        BaseInfo subBaseInfo = BaseInfo.builder()
                                .groupName(groupName88)
                                .code(String.format("%s_%03d", code, j + 1))
                                .description(subCategoryDescriptions[j])
                                .parentCode(code)
                                .build();
                        baseInfoRepository.save(subBaseInfo);
                    }
                }
            }
        }
    }
}
