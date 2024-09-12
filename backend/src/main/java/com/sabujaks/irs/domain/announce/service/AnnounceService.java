package com.sabujaks.irs.domain.announce.service;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import com.sabujaks.irs.domain.announce.model.entity.CustomForm;
import com.sabujaks.irs.domain.announce.model.entity.CustomLetterForm;
import com.sabujaks.irs.domain.announce.model.request.AnnounceRegisterReq;
import com.sabujaks.irs.domain.announce.model.request.CustomFormReq;
import com.sabujaks.irs.domain.announce.model.response.AnnounceRegisterRes;
import com.sabujaks.irs.domain.announce.model.response.CustomFormRes;
import com.sabujaks.irs.domain.announce.repository.AnnounceRepository;
import com.sabujaks.irs.domain.announce.repository.CustomFormRepository;
import com.sabujaks.irs.domain.announce.repository.CustomLetterFormRepository;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import com.sabujaks.irs.domain.data_init.repository.BaseInfoRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnounceService {
    private final RecruiterRepository recruiterRepository;
    private final AnnounceRepository announceRepository;
    private final CustomFormRepository customFormRepository;
    private final CustomLetterFormRepository letterFormRepository;
    private final BaseInfoRepository baseInfoRepository;

    /*******채용담당자 공고 등록 (step1)***********/
    public AnnounceRegisterRes registerAnnounce(
            Long recruiterIdx,
            String fileUrl,
            AnnounceRegisterReq dto) throws BaseException {

        // 채용담당자 확인
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isPresent()) {
            // 셀렉트 폼이 트루면 = 파일url이 있으면 파일만 공고 엔티티에 저장
            if(dto.getSelectForm()) {
                Announcement announcement = Announcement.builder()
                        .recruiter(resultRecruiter.get())
                        .selectForm(dto.getSelectForm())
                        .title(dto.getTitle())
                        .imgUrl(fileUrl)
                        .build();
                announceRepository.save(announcement);

                // 응답
                return AnnounceRegisterRes.builder()
                        .announceIdx(announcement.getIdx())
                        .build();

            } else { // 셀렉트 폼이 폴스면 = 파일url이 없으면 들어온 dto만 저장
                Announcement announcement = Announcement.builder()
                        .recruiter(resultRecruiter.get())
                        .selectForm(dto.getSelectForm())
                        .title(dto.getTitle())
                        .jobCategory(dto.getJobCategoryList().toString())
                        .jobTitle(dto.getJobTitle())
                        .recruitedNum(dto.getRecruitedNum())
                        .careerBase(dto.getCareerBase())
                        .intro(dto.getIntro())
                        .positionQuali(dto.getPositionQuali())
                        .jobType(dto.getJobType())
                        .salary(dto.getSalary())
                        .conditions(dto.getConditions())
                        .region(dto.getRegion())
                        .benefits(dto.getBenefits())
                        .process(dto.getProcess())
                        .announcementStart(dto.getAnnouncementStart())
                        .announcementEnd(dto.getAnnouncementEnd())
                        .interviewNum(dto.getInterviewNum())
                        .note(dto.getNote())
                        .build();
                announceRepository.save(announcement);

                // 응답
                return AnnounceRegisterRes.builder()
                        .announceIdx(announcement.getIdx())
                        .build();
            }
        } else {
            // 채용담당자 유저에서 찾을 수 없을 때
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }
    }


    /*******채용담당자 지원서 폼 조립 + 자기소개서 문항 등록 (step2)***********/
    public CustomFormRes registerCustomForm(CustomFormReq dto) throws BaseException {
        // 클라이언트에서 넣을 폼을 선택 -> dto에 그 폼의 코드값이 들어옴

        // 예외 처리) 공고가 잘 저장되어 있는지 먼저 확인 필요
        Optional<Announcement> resultAnnouncement = announceRepository.findByAnnounceIdx(dto.getAnnounceIdx());
        if (!resultAnnouncement.isPresent()) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_NOT_FOUND);
        }

        // 1. 리스트 길이만큼 커스텀 폼 엔티티 생성 후 저장
        List<String> formCodes = dto.getCode();
        for (String code : formCodes) {
            CustomForm customForm = CustomForm.builder()
                    .code(code)
                    .announcement(resultAnnouncement.get())
                    .build();
            customFormRepository.save(customForm);
        }

        // 2. baseInfo 조회 - groupName과 code 리스트로 조회
        String groupName = "custom_form";
        List<BaseInfo> resultBaseInfoList = baseInfoRepository.findAllByGroupNameAndCodeIn(groupName, formCodes);

        // 3. 저장 배열 생성 (인덱스와 설명 리스트)
        List<Long> emsiIdx = resultBaseInfoList.stream()
                .map(BaseInfo::getIdx)
                .collect(Collectors.toList());

        List<String> emsiDes = resultBaseInfoList.stream()
                .map(BaseInfo::getDescription)
                .collect(Collectors.toList());

        // 4. 추가한 자기소개서 문항 등록 (리스트 길이만큼 커스텀 레터 폼 엔티티 생성 후 저장)
        List<Long> emsiLetterIdx = new ArrayList<>();
        for (int i = 0; i < dto.getTitleList().size(); i++) {
            CustomLetterForm customLetterForm = CustomLetterForm.builder()
                    .title(dto.getTitleList().get(i))
                    .chatLimit(dto.getChatLimitList().get(i))
                    .announcement(resultAnnouncement.get())
                    .build();
            letterFormRepository.save(customLetterForm);
            emsiLetterIdx.add(customLetterForm.getIdx());
        }

        // 5. 응답 생성
        return CustomFormRes.builder()
                .baseInfoIdxList(emsiIdx)
                .descriptionList(emsiDes)
                .customFormLetterIdList(emsiLetterIdx)
                .build();
    }
}
