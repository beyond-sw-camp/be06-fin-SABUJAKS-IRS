package com.example.api.global.utils.email.service;

import com.example.api.domain.resume.model.response.ResumeReadAllRecruiterRes;
import com.example.api.global.security.CustomUserDetails;
import com.example.api.global.utils.email.model.response.ResumeResultRes;
import com.example.common.domain.company.model.entity.Company;
import com.example.common.domain.company.repository.CompanyRepository;
import com.example.common.domain.resume.model.entity.Resume;
import com.example.common.domain.resume.repository.ResumeRepository;
import com.example.common.domain.total_process.repository.TotalProcessRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    private final ResumeRepository resumeRepository;
    private final CompanyRepository companyRepository;
    private final TotalProcessRepository totalProcessRepository;

    public EmailService(ResumeRepository resumeRepository, CompanyRepository companyRepository, TotalProcessRepository totalProcessRepository) {
        this.resumeRepository = resumeRepository;
        this.companyRepository = companyRepository;
        this.totalProcessRepository = totalProcessRepository;
    }

    public List<ResumeResultRes> getInfo(CustomUserDetails customUserDetails, List<ResumeReadAllRecruiterRes> resumeList) {
        Company company = companyRepository.findByRecruiterIdx(customUserDetails.getIdx()).get();

        List<ResumeResultRes> resumeResultResList = new ArrayList<>();

        for(ResumeReadAllRecruiterRes res: resumeList) {
            Resume resume = resumeRepository.findByResumeIdx(res.getResumeIdx()).get();

            resumeResultResList.add(ResumeResultRes.builder()
                            .seekerIdx(resume.getSeeker().getIdx())
                            .seekerName(resume.getSeeker().getName())
                            .seekerEmail(resume.getSeeker().getEmail())
                            .companyName(company.getName())
                            .announcementTitle(resume.getAnnouncement().getTitle())
                            .resumeIdx(resume.getIdx())
                            .announcementIdx(resume.getAnnouncement().getIdx())
                            .build());
        }

        return resumeResultResList;
    }
}
