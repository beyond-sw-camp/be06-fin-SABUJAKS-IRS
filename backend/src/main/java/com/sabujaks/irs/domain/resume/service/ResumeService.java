package com.sabujaks.irs.domain.resume.service;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.resume.model.entity.*;
import com.sabujaks.irs.domain.resume.model.request.*;
import com.sabujaks.irs.domain.resume.model.response.ResumeCreateRes;
import com.sabujaks.irs.domain.resume.repository.*;
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
    private final ResumeInfoRepository resumeInfoRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final PreferentialEmpRepository preferentialEmpRepository;
    private final EducationRepository educationRepository;
    private final PersonalHistoryRepository personalHistoryRepository;
    private final InternActivitiesRepository internActivitiesRepository;
    private final StudyingAboardRepository studyingAboardRepository;
    private final LanguageRepository languageRepository;


    @Transactional
    public ResumeCreateRes create(Long seekerIdx, ResumeCreateReq dto, String fileUrl) throws BaseException {
        // 지원자 테이블 조회
        Optional<Seeker> result = seekerRepository.findBySeekerIdx(seekerIdx);
        if(result.isPresent()) {
            // 지원정보 테이블에 저장
            ResumeInfo resumeInfo = ResumeInfo.builder()
                    .seeker(result.get())
                    .build();
            resumeInfoRepository.save(resumeInfo);
            // 지원정보 양식 테이블에 저장, 테이블 생성해야함
//            for(String code : dto.getCodes()) {
//
//            }
            System.out.println(dto.getCodes());
            // 인적사항 테이블에 저장 (반드시)
            PersonalInfo personalInfo = PersonalInfo.builder()
                    .resumeInfo(resumeInfo)
                    .name(dto.getPersonalInfo().getName())
                    .birth(dto.getPersonalInfo().getBirth())
                    .gender(dto.getPersonalInfo().getGender())
                    .email(dto.getPersonalInfo().getEmail())
                    .phone(dto.getPersonalInfo().getPhone())
                    .tel(dto.getPersonalInfo().getTel())
                    .address(dto.getPersonalInfo().getAddress())
                    .profile_img(fileUrl)
                    .build();
            personalInfoRepository.save(personalInfo);
            // 취업우대&병역에 저장 (조건 필요)
            PreferentialEmp preferentialEmp = PreferentialEmp.builder()
                    .resumeInfo(resumeInfo)
                    .veterans(dto.getPreferentialEmp().getVeterans())
                    .protection(dto.getPreferentialEmp().getProtection())
                    .subsidy(dto.getPreferentialEmp().getSubsidy())
                    .disability(dto.getPreferentialEmp().getDisability())
                    .disability_degree(dto.getPreferentialEmp().getDisability_degree())
                    .military(dto.getPreferentialEmp().getMilitary())
                    .military_class(dto.getPreferentialEmp().getMilitary_class())
                    .military_start(dto.getPreferentialEmp().getMilitary_start())
                    .military_end(dto.getPreferentialEmp().getMilitary_end())
                    .military_type(dto.getPreferentialEmp().getMilitary_type())
                    .military_rank(dto.getPreferentialEmp().getMilitary_rank())
                    .build();
            preferentialEmpRepository.save(preferentialEmp);

            // 학력 테이블에 저장 (조건 필요)
            for(EducationCreateReq edu : dto.getEducations()) {
                Education education = Education.builder()
                        .resumeInfo(resumeInfo)
                        .high_less(edu.getHigh_less())
                        .school_div(edu.getSchool_div())
                        .school_name(edu.getSchool_name())
                        .entered_at(edu.getEntered_at())
                        .graduated_at(edu.getGraduated_at())
                        .graduation_status(edu.getGraduation_status())
                        .major_name(edu.getMajor_name())
                        .grade(edu.getGrade())
                        .total_grade(edu.getTotal_grade())
                        .transfer(edu.getTransfer())
                        .major_type(edu.getMajor_type())
                        .other_major(edu.getOther_major())
                        .graduation_work(edu.getGraduation_work())
                        .degree(edu.getDegree())
                        .qualification_exam(edu.getQualification_exam())
                        .passed_at(edu.getPassed_at())
                        .build();
                educationRepository.save(education);
            }


            // 경력 테이블에 저장 (조건 필요)
            for(PersonalHistoryCreateReq ph : dto.getPersonalHistories()) {
                PersonalHistory personalHistory = PersonalHistory.builder()
                        .resumeInfo(resumeInfo)
                        .company_name(ph.getCompany_name())
                        .dept_name(ph.getDept_name())
                        .entered_at(ph.getEntered_at())
                        .quit_at(ph.getQuit_at())
                        .emp_status(ph.getEmp_status())
                        .position(ph.getPosition())
                        .job(ph.getJob())
                        .salary(ph.getSalary())
                        .work(ph.getWork())
                        .build();
                personalHistoryRepository.save(personalHistory);
            }

            // 인턴·대외활동 테이블에 저장 (조건 필요)
            for(InternsActivityCreateReq ia : dto.getInternsActivities()) {
                InternsActivity internsActivity = InternsActivity.builder()
                        .resumeInfo(resumeInfo)
                        .activityDiv(ia.getActivityDiv())
                        .organization(ia.getOrganization())
                        .startAt(ia.getStartAt())
                        .endAt(ia.getEndAt())
                        .contents(ia.getContents())
                        .build();
                internActivitiesRepository.save(internsActivity);
            }

            // 해외경험 테이블에 저장 (조건 필요)
            for(StudyingAbroadCreateReq sa : dto.getStudyingAbroads()) {
                StudyingAbroad studyingAbroad = StudyingAbroad.builder()
                        .resumeInfo(resumeInfo)
                        .countryName(sa.getCountryName())
                        .startAt(sa.getStartAt())
                        .endAt(sa.getEndAt())
                        .contents(sa.getContents())
                        .build();
                studyingAboardRepository.save(studyingAbroad);
            }

            // 어학 테이블에 저장 (조건 필요)
            for(LanguageCreateReq l : dto.getLanguages()) {
                Language language = Language.builder()
                        .resumeInfo(resumeInfo)
                        .testDiv(l.getTestDiv())
                        .languageName(l.getLanguageName())
                        .conversationLevel(l.getConversationLevel())
                        .officialTest(l.getOfficialTest())
                        .score(l.getScore())
                        .takingAt(l.getTakingAt())
                        .build();
                languageRepository.save(language);
            }

            return ResumeCreateRes.builder()
                    .resume_info_idx(resumeInfo.getIdx())
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND);
        }
    }
}
