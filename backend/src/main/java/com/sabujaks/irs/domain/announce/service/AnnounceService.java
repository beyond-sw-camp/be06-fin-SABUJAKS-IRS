package com.sabujaks.irs.domain.announce.service;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import com.sabujaks.irs.domain.announce.model.entity.CustomForm;
import com.sabujaks.irs.domain.announce.model.entity.CustomLetterForm;
import com.sabujaks.irs.domain.announce.model.request.CustomFormReq;
import com.sabujaks.irs.domain.announce.model.response.CustomFormRes;
import com.sabujaks.irs.domain.announce.repository.AnnounceRepository;
import com.sabujaks.irs.domain.announce.repository.CustomFormRepository;
import com.sabujaks.irs.domain.announce.repository.CustomLetterFormRepository;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import com.sabujaks.irs.domain.data_init.repository.BaseInfoRepository;
import com.sabujaks.irs.domain.resume.model.response.ResumeCreateRes;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnounceService {
    private final AnnounceRepository announceRepository;
    private final CustomFormRepository customFormRepository;
    private final CustomLetterFormRepository letterFormRepository;
    private final BaseInfoRepository baseInfoRepository;


    /*******채용담당자 지원서 폼 조립 + 자기소개서 문항 등록 (step2)***********/
    public CustomFormRes registerCustomForm(CustomFormReq dto) throws BaseException {
        //클라이언트에서 넣을 폼을 선택한다 -> dto에 그 폼의 코드값이 들어온다

        //예외 처리) 공고가 잘 저장되어 있는지 먼저 확인 필요
        Optional<Announcement> resultAnnouncement = announceRepository.findByAnnounceIdx(dto.getAnnounceIdx());
        if (resultAnnouncement.isPresent()) {
            //리스트 길이만큼 커스텀 폼 엔티티 생성 후 저장
            for(int i=0; i < dto.getCode().size(); i++) {
                CustomForm customForm = CustomForm.builder()
                        .code(dto.getCode().get(i))
                        .announcement(resultAnnouncement.get())
                        .build();
                customFormRepository.save(customForm);
            }

            String groupName = "custom_form";
            List<BaseInfo> resultBaseInfoList = baseInfoRepository.findAllByGroupName(groupName);

            //저장 배열
            List<Long> emsiIdx = new ArrayList<>();
            List<String> emsiDes = new ArrayList<>();

            for(int i=0; i < resultBaseInfoList.size(); i++) {
                for(int j=0; j < dto.getCode().size(); j++) {
                    if(Objects.equals(resultBaseInfoList.get(i).getCode() , dto.getCode().get(j))) {
                        emsiIdx.add(resultBaseInfoList.get(i).getIdx());
                        emsiDes.add(resultBaseInfoList.get(i).getDescription());
                    }
                }
            }

            // 추가한 자기소개서 문항 등록

            // 저장 배열
            List<Long> emsiLetterIdx = new ArrayList<>();

            //리스트 길이만큼 커스텀 레터 폼 엔티티 생성 후 저장
            for(int i=0; i < dto.getTitleList().size(); i++) {
                CustomLetterForm customLetterForm = CustomLetterForm.builder()
                        .title(dto.getTitleList().get(i))
                        .chatLimit(dto.getChatLimitList().get(i))
                        .announcement(resultAnnouncement.get())
                        .build();
                letterFormRepository.save(customLetterForm);
                emsiLetterIdx.add(customLetterForm.getIdx());
            }

            //응답
            return CustomFormRes.builder()
                    .baseInfoIdxList(emsiIdx)
                    .descriptionList(emsiDes)
                    .customFormLetterIdList(emsiLetterIdx)
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_NOT_FOUND);
        }

    }
}
