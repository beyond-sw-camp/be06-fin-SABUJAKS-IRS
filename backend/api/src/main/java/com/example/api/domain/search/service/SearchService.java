package com.example.api.domain.search.service;

import com.example.api.domain.announcement.model.response.AnnouncementReadAllRes;
import com.example.api.global.common.annotation.ExeTimer;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.announcement.repository.querydsl.AnnouncementDslRepository;
import com.example.common.domain.company.model.entity.*;
import com.example.common.domain.company.repository.CompanyRepository;
import com.example.common.domain.search.model.request.SearchReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final AnnouncementDslRepository announcementDslRepository;
    private final CompanyRepository companyRepository;

    // 키워드로 공고 검색
    @ExeTimer
    public Page<AnnouncementReadAllRes> search(SearchReq searchReq) throws BaseException {
        Pageable pageable = PageRequest.of(searchReq.getPage(), searchReq.getSize());

        Page<Announcement> resultAnnouncements = announcementDslRepository.search(pageable, searchReq);

        if (resultAnnouncements.hasContent()) {
            List<AnnouncementReadAllRes> resultReadAllResList = new ArrayList<>();
            for (Announcement am : resultAnnouncements) {
                Company company = companyRepository.findByRecruiterIdx(am.getRecruiter().getIdx())
                        .orElseThrow(()-> new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER));

                // 기업 이미지 url 리스트 넣기
                List<String> imgList = new ArrayList<>();
                for(CompanyImg ci : company.getCompanyImgList()){
                    imgList.add(ci.getUrl());
                }

                resultReadAllResList.add(
                        AnnouncementReadAllRes.builder()
                                .announcementIdx(am.getIdx())
                                .companyName(company.getName())
                                .companyInfo(company.getCompanyInfo())
                                .announcementTitle(am.getTitle())
                                .jobTitle(am.getJobTitle())
                                .careerBase(am.getCareerBase())
                                .region(am.getRegion())
                                .announcementEnd(am.getAnnouncementEnd())
                                .imgList(imgList)
                                .build()
                );
            }
            return new PageImpl<>(resultReadAllResList, pageable, resultAnnouncements.getTotalElements());
        } else {
            return new PageImpl<>(new ArrayList<>(), pageable, 0); // 빈 페이지 반환
        }
    }
}
