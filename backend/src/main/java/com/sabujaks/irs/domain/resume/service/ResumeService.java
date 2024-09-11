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
    private final CertificationRepository certificationRepository;
    private final TrainingRepository trainingRepository;
    private final AwardRepository awardRepository;


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
                    .profileImg(fileUrl)
                    .build();
            personalInfoRepository.save(personalInfo);
            // 취업우대&병역에 저장 (조건 필요)
            PreferentialEmp preferentialEmp = PreferentialEmp.builder()
                    .resumeInfo(resumeInfo)
                    .veterans(dto.getPreferentialEmp().getVeterans())
                    .protection(dto.getPreferentialEmp().getProtection())
                    .subsidy(dto.getPreferentialEmp().getSubsidy())
                    .disability(dto.getPreferentialEmp().getDisability())
                    .disabilityDegree(dto.getPreferentialEmp().getDisabilityDegree())
                    .military(dto.getPreferentialEmp().getMilitary())
                    .militaryClass(dto.getPreferentialEmp().getMilitaryClass())
                    .militaryStart(dto.getPreferentialEmp().getMilitaryStart())
                    .militaryEnd(dto.getPreferentialEmp().getMilitaryEnd())
                    .militaryType(dto.getPreferentialEmp().getMilitaryType())
                    .militaryRank(dto.getPreferentialEmp().getMilitaryRank())
                    .build();
            preferentialEmpRepository.save(preferentialEmp);

            // 학력 테이블에 저장 (조건 필요)
            for(EducationCreateReq edu : dto.getEducations()) {
                Education education = Education.builder()
                        .resumeInfo(resumeInfo)
                        .highLess(edu.getHighLess())
                        .schoolDiv(edu.getSchoolDiv())
                        .schoolName(edu.getSchoolName())
                        .enteredAt(edu.getEnteredAt())
                        .graduatedAt(edu.getGraduatedAt())
                        .graduationStatus(edu.getGraduationStatus())
                        .majorName(edu.getMajorName())
                        .grade(edu.getGrade())
                        .totalGrade(edu.getTotalGrade())
                        .transfer(edu.getTransfer())
                        .majorType(edu.getMajorType())
                        .otherMajor(edu.getOtherMajor())
                        .graduationWork(edu.getGraduationWork())
                        .degree(edu.getDegree())
                        .qualificationExam(edu.getQualificationExam())
                        .passedAt(edu.getPassedAt())
                        .build();
                educationRepository.save(education);
            }

            // 경력 테이블에 저장 (조건 필요)
            for(PersonalHistoryCreateReq ph : dto.getPersonalHistories()) {
                PersonalHistory personalHistory = PersonalHistory.builder()
                        .resumeInfo(resumeInfo)
                        .companyName(ph.getCompanyName())
                        .deptName(ph.getDeptName())
                        .enteredAt(ph.getEnteredAt())
                        .quitAt(ph.getQuitAt())
                        .empStatus(ph.getEmpStatus())
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

            // 자격증 테이블에 저장 (조건 필요)
            for(CertificationCreateReq c : dto.getCertifications()) {
                Certification certification = Certification.builder()
                        .resumeInfo(resumeInfo)
                        .certName(c.getCertName())
                        .organization(c.getOrganization())
                        .takingAt(c.getTakingAt())
                        .build();
                certificationRepository.save(certification);
            }

            // 교육이수 테이블에 저장 (조건 필요)
            for(TrainingCreateReq t : dto.getTrainings()) {
                Training training = Training.builder()
                        .resumeInfo(resumeInfo)
                        .trainingName(t.getTrainingName())
                        .organization(t.getOrganization())
                        .startAt(t.getStartAt())
                        .endAt(t.getEndAt())
                        .contents(t.getContents())
                        .build();
                trainingRepository.save(training);
            }

            // 수상 테이블에 저장 (조건 필요)
            for(AwardCreateReq a : dto.getAwards()) {
                Award award = Award.builder()
                        .resumeInfo(resumeInfo)
                        .awardName(a.getAwardName())
                        .contents(a.getContents())
                        .organization(a.getOrganization())
                        .year(a.getYear())
                        .build();
                awardRepository.save(award);
            }

            return ResumeCreateRes.builder()
                    .resume_info_idx(resumeInfo.getIdx())
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND);
        }
    }
}
