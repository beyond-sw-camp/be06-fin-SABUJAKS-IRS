package com.sabujaks.irs.domain.search.service;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.model.response.AnnouncementReadAllRes;
import com.sabujaks.irs.domain.announcement.repository.AnnouncementRepository;
import com.sabujaks.irs.domain.company.repository.CompanyRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final AnnouncementRepository announcementRepository;
    private final CompanyRepository companyRepository;

    // 키워드로 공고 검색
    public List<AnnouncementReadAllRes> searchByKeyword(String keyword) throws BaseException {
        // 공고명 또는 모집분야에 키워드가 포함되어 있는지 여부
        Optional<List<Announcement>> resultAnnounceKeywordList =
                announcementRepository.findAllByTitleContainingOrjobTitleContaining(keyword, keyword);

        if (resultAnnounceKeywordList.isPresent()) {
            // 찾은 공고들이 있으면 리스트로 넣어주기
            List<AnnouncementReadAllRes> resultReadAllResList = new ArrayList<>();
            for (Announcement am : resultAnnounceKeywordList.get()) {
                resultReadAllResList.add(
                        AnnouncementReadAllRes.builder()
                                .announcementIdx(am.getIdx())
                                .companyName(companyRepository.findByRecruiterIdx(am.getRecruiter().getIdx())
                                        .orElseThrow(()-> new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER))
                                        .getName())
                                .announcementTitle(am.getTitle())
                                .jobTitle(am.getJobTitle())
                                .careerBase(am.getCareerBase())
                                .region(am.getRegion())
                                .announcementEnd(am.getAnnouncementEnd())
                                .build()
                );

            }
            return resultReadAllResList;
        } else {
            return new ArrayList<>();
        }

    }
}
