package com.sabujaks.irs.domain.announce.service;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import com.sabujaks.irs.domain.announce.model.entity.CustomForm;
import com.sabujaks.irs.domain.announce.model.request.CustomFormReq;
import com.sabujaks.irs.domain.announce.model.response.CustomFormRes;
import com.sabujaks.irs.domain.announce.repository.AnnounceRepository;
import com.sabujaks.irs.domain.announce.repository.CustomFormRepository;
import com.sabujaks.irs.domain.announce.repository.CustomLetterFormRepository;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.resume.model.response.ResumeCreateRes;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnounceService {
    private final AnnounceRepository announceRepository;
    private final CustomFormRepository customFormRepository;
    private final CustomLetterFormRepository letterFormRepository;


    /*******채용담당자 지원서 폼 조립 (step2)***********/
    public CustomFormRes registerCustomForm(Long announceIdx, CustomFormReq dto) throws BaseException {
        //클라이언트에서 넣을 폼을 선택한다 -> dto에 그 폼의 코드값이 들어온다

        //예외 처리) 공고가 잘 저장되어 있는지 먼저 확인 필요
        Optional<Announcement> result = announceRepository.findByAnnounceIdx(announceIdx);
        if (result.isPresent()) {
            //임시 배열
            List<String> emsi = new ArrayList<>();

            //리스트 길이만큼 커스텀 폼 엔티티 생성 후 저장
            for(int i=0; i < dto.getCode().size(); i++) {
                CustomForm customForm = CustomForm.builder()
                        .code(dto.getCode().get(i))
                        .announcement(result.get())
                        .build();
                customFormRepository.save(customForm);
                emsi.add(dto.getCode().get(i));
            }

            //응답 (현재는 코드를 그대로 보이기 -> 추후 폼 이름 출력으로 수정 예정)
            return CustomFormRes.builder()
                    .code(emsi)
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_NOT_FOUND);
        }

    }
}
