package com.sabujaks.irs.global.utils.email.service;

import com.sabujaks.irs.domain.company.model.entity.Company;
import com.sabujaks.irs.domain.company.repository.CompanyRepository;
import com.sabujaks.irs.domain.resume.model.entity.Resume;
import com.sabujaks.irs.domain.resume.model.response.ResumeReadAllRecruiterRes;
import com.sabujaks.irs.domain.resume.repository.ResumeRepository;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.email.model.response.ResumeRejectRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final ResumeRepository resumeRepository;
    private final CompanyRepository companyRepository;

    public List<ResumeRejectRes> getRejectInfo(CustomUserDetails customUserDetails, List<ResumeReadAllRecruiterRes> resumeList) {

        Company company = companyRepository.findByRecruiterIdx(customUserDetails.getIdx()).get();

        List<ResumeRejectRes> resumeRejectResList = new ArrayList<>();
        for(ResumeReadAllRecruiterRes res: resumeList) {
            Resume resume;
            if(res.getResumeResult()) {
                resume = resumeRepository.findByResumeIdx(res.getResumeIdx()).get();
            } else {
                continue;
            }

            resumeRejectResList.add(ResumeRejectRes.builder()
                            .seekerIdx(resume.getSeeker().getIdx())
                            .seekerName(resume.getSeeker().getName())
                            .seekerEmail(resume.getSeeker().getEmail())
                            .companyName(company.getName())
                            .announcementTitle(resume.getAnnouncement().getTitle())
                            .build());
        }

        return resumeRejectResList;
    }
}
