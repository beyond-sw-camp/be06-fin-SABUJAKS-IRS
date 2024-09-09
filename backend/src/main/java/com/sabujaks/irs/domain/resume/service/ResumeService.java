package com.sabujaks.irs.domain.resume.service;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.resume.model.entity.PersonalInfo;
import com.sabujaks.irs.domain.resume.model.entity.PreferentialEmp;
import com.sabujaks.irs.domain.resume.model.entity.ResumeInfo;
import com.sabujaks.irs.domain.resume.model.request.ResumeCreateReq;
import com.sabujaks.irs.domain.resume.model.response.ResumeCreateRes;
import com.sabujaks.irs.domain.resume.repository.PersonalInfoRepository;
import com.sabujaks.irs.domain.resume.repository.PreferentialEmpRepository;
import com.sabujaks.irs.domain.resume.repository.ResumeInfoRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final SeekerRepository seekerRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final PreferentialEmpRepository personPreferentialEmpRepository;
    private final ResumeInfoRepository resumeInfoRepository;
    private final PreferentialEmpRepository preferentialEmpRepository;


    @Transactional
    public ResumeCreateRes create(Long seekerIdx, ResumeCreateReq dto, String fileUrl) throws BaseException {
        // 회원 테이블 조회
        Optional<Seeker> result = seekerRepository.findBySeekerIdx(seekerIdx);
        if(result.isPresent()) {
            // 지원정보 테이블
            ResumeInfo resumeInfo = ResumeInfo.builder()
                    .seeker(result.get())
                    .build();
            resumeInfoRepository.save(resumeInfo);
            // 인적사항 테이블
            PersonalInfo personalInfo = PersonalInfo.builder()
                    .resumeInfo(resumeInfo)
                    .name(dto.getPersonalInfo().getName())
                    .birth(dto.getPersonalInfo().getBirth())
                    .gender(dto.getPersonalInfo().getGender())
                    .email(dto.getPersonalInfo().getEmail())
                    .phone(dto.getPersonalInfo().getPhone())
                    .tel(dto.getPersonalInfo().getTel())
                    .address(dto.getPersonalInfo().getAddress())
                    .profileImg(fileUrl)
                    .build();
            personalInfoRepository.save(personalInfo);
            // 취업우대&병역 테이블
            PreferentialEmp preferentialEmp = PreferentialEmp.builder()
                    .resumeInfo(resumeInfo)
                    .veterans(dto.getPreferentialEmpInfo().getVeterans())
                    .protection(dto.getPreferentialEmpInfo().getProtection())
                    .subsidy(dto.getPreferentialEmpInfo().getSubsidy())
                    .disability(dto.getPreferentialEmpInfo().getDisability())
                    .disabilityDegree(dto.getPreferentialEmpInfo().getDisability_degree())
                    .military(dto.getPreferentialEmpInfo().getMilitary())
                    .militaryClass(dto.getPreferentialEmpInfo().getMilitary_class())
                    .militaryStart(dto.getPreferentialEmpInfo().getMilitary_start())
                    .militaryEnd(dto.getPreferentialEmpInfo().getMilitary_end())
                    .militaryType(dto.getPreferentialEmpInfo().getMilitary_type())
                    .militaryRank(dto.getPreferentialEmpInfo().getMilitary_rank())
                    .build();
            preferentialEmpRepository.save(preferentialEmp);

            return ResumeCreateRes.builder()
                    .resume_info_idx(resumeInfo.getIdx())
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND);
        }
    }
}
