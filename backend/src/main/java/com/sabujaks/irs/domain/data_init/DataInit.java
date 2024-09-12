package com.sabujaks.irs.domain.data_init;

import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import com.sabujaks.irs.domain.data_init.repository.BaseInfoRepository;
import com.sabujaks.irs.domain.interview_schedule.model.entity.Team;
import com.sabujaks.irs.domain.interview_schedule.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInit {
    private final BaseInfoRepository baseInfoRepository;
    private final TeamRepository teamRepository;

    // 어노테이션 주석 해제하면 init 됨
    // yml create로 바꾸고 주석해제(한번 넣기) 프로젝트 실행, 그 후 주석처리 안하고 프로젝트 돌리면 중복으로 들어가니까 다시 주석처리하기!
//    @PostConstruct
    public void dataInsert() {

        // 지원서 폼 등록
        String groupName = "custom_form";
        String[] descriptions = {
                "학력", "경력", "인턴&대외활동", "교육이수", "자격증",
                "수상", "해외경험", "어학", "포트폴리오", "취업우대&병역", "자기소개서"
        };

        for (int i = 0; i < descriptions.length; i++) {
            BaseInfo baseInfo = BaseInfo.builder()
                    .groupName(groupName)
                    .code(String.format("resume_%03d", i + 1))  // resume_001 부터 순차 증가
                    .description(descriptions[i])
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
