package com.sabujaks.irs.domain.resume.service;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.model.entity.CustomForm;
import com.sabujaks.irs.domain.announcement.model.entity.CustomLetterForm;
import com.sabujaks.irs.domain.announcement.repository.AnnouncementRepository;
import com.sabujaks.irs.domain.announcement.repository.CustomFormRepository;
import com.sabujaks.irs.domain.announcement.repository.CustomLetterFormRepository;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.company.model.entity.Company;
import com.sabujaks.irs.domain.company.repository.CompanyRepository;
import com.sabujaks.irs.domain.resume.model.entity.*;
import com.sabujaks.irs.domain.resume.model.request.*;
import com.sabujaks.irs.domain.resume.model.response.*;
import com.sabujaks.irs.domain.resume.repository.*;
import com.sabujaks.irs.domain.total_process.model.entity.TotalProcess;
import com.sabujaks.irs.domain.total_process.repository.TotalProcessRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private final CustomLetterRepository customLetterRepository;
    private final PortfolioRepository portfolioRepository;
    private final CustomResumeInfoRepository customResumeInfoRepository;
    private final AnnouncementRepository announcementRepository;
    private final ResumeRepository resumeRepository;
    private final CustomLetterFormRepository customLetterFormRepository;
    private final CustomFormRepository customFormRepository;
    private final RecruiterRepository recruiterRepository;
    private final CompanyRepository companyRepository;
    private final TotalProcessRepository totalProcessRepository;


    @Transactional
    public ResumeCreateRes create(CustomUserDetails customUserDetails, ResumeCreateReq dto, String fileUrl) throws BaseException {
        Long seekerIdx = customUserDetails.getIdx();
        // 지원자 테이블 조회
        Optional<Seeker> resultSeeker = seekerRepository.findBySeekerIdx(seekerIdx);
        if(resultSeeker.isPresent()) {
            // 통합지원서 저장 여부 파악 (해당 지원자가 통합 지원서를 작성한 적이 있는지)
            Optional<ResumeInfo> resultResumeInfo = resumeInfoRepository.findBySeekerIdxAndIntegrated(seekerIdx, true);
            if(resultResumeInfo.isPresent()) {
                throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_INTEGRATED);
            }
            // 지원정보 테이블에 저장
            ResumeInfo resumeInfo = ResumeInfo.builder()
                    .seeker(resultSeeker.get())
                    .integrated(true) // 통합지원서 - 1개만 가능
                    .build();
            resumeInfoRepository.save(resumeInfo);

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

            // 맞춤코드에 해당하는 테이블에 저장 후 지원정보 양식 테이블에 저장
            for(String code : dto.getCodes()) {
                if(code.equals("resume_001") && dto.getEducations() != null) {
                    // 학력 테이블에 저장 (조건 필요)
                    for(EducationCreateReq edu : dto.getEducations()) {
                        Education education = Education.builder()
                                .resumeInfo(resumeInfo)
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
                } else if(code.equals("resume_002") && dto.getPersonalHistories() != null) {
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

                } else if(code.equals("resume_003") && dto.getInternsActivities() != null) {
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
                } else if(code.equals("resume_004") && dto.getTrainings() != null) {
                    // 교육이수 테이블에 저장 (조건 필요) resume_004
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
                } else if(code.equals("resume_005") && dto.getCertifications() != null) {
                    // 자격증 테이블에 저장 (조건 필요) resume_005
                    for(CertificationCreateReq c : dto.getCertifications()) {
                        Certification certification = Certification.builder()
                                .resumeInfo(resumeInfo)
                                .certName(c.getCertName())
                                .organization(c.getOrganization())
                                .takingAt(c.getTakingAt())
                                .build();
                        certificationRepository.save(certification);
                    }
                } else if(code.equals("resume_006") && dto.getAwards() != null) {
                    // 수상 테이블에 저장 (조건 필요) resume_006
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
                } else if(code.equals("resume_007") && dto.getStudyingAbroads() != null) {
                    // 해외경험 테이블에 저장 (조건 필요) resume_007
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
                } else if(code.equals("resume_008") && dto.getLanguages() != null) {
                    // 어학 테이블에 저장 (조건 필요) resume_008
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
                } else if(code.equals("resume_009") && dto.getPortfolios() != null) {
                    // 포트폴리오 테이블에 저장 (조건 필요) resume_009
                    for(PortfolioCreateReq p : dto.getPortfolios()) {
                        Portfolio portfolio = Portfolio.builder()
                                .resumeInfo(resumeInfo)
                                .portfolioDiv(p.getPortfolioDiv())
                                .portfolioType(p.getPortfolioType())
                                .portfolioUrl(p.getPortfolioUrl())
                                .build();
                        portfolioRepository.save(portfolio);
                    }
                } else if(code.equals("resume_010") && dto.getPreferentialEmp() != null) {
                    // 취업우대&병역에 저장 (조건 필요) resume_010
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
                } else if(code.equals("resume_011") && dto.getCustomLetters() != null) {
                    // 자기소개서 테이블에 저장 (조건 필요) resume_011
                    for(CustomLetterCreateReq cl : dto.getCustomLetters()) {
                        CustomLetter customLetter = CustomLetter.builder()
                                .resumeInfo(resumeInfo)
                                .title(cl.getTitle())
                                .charNum(cl.getCharNum())
                                .contents(cl.getContents())
                                .build();
                        customLetterRepository.save(customLetter);
                    }
                } else {
                    throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL);
                }

                CustomResumeInfo customResumeInfo = CustomResumeInfo.builder()
                        .resumeInfo(resumeInfo)
                        .code(code)
                        .build();
                customResumeInfoRepository.save(customResumeInfo);
            }

            return ResumeCreateRes.builder()
                    .resumeInfoIdx(resumeInfo.getIdx())
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER);
        }
    }

    @Transactional
    public ResumeSubmitRes submit(CustomUserDetails customUserDetails, ResumeSubmitReq dto, String fileUrl) throws BaseException {
        Long seekerIdx = customUserDetails.getIdx();
        // 지원자 테이블 조회
        Optional<Seeker> resultSeeker = seekerRepository.findBySeekerIdx(seekerIdx);
        if(resultSeeker.isPresent()) {
            // 공고 idx로 공고 조회
            Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnounceIdx(dto.getAnnouncementIdx());
            if(resultAnnouncement.isPresent()) {
                // 지원자가 해당 공고에 이미 지원했는지 확인
                Optional<Resume> resultResume = resumeRepository.findByAnnouncementIdxAndSeekerIdx(dto.getAnnouncementIdx(), seekerIdx);
                if(resultResume.isEmpty()) {
                    // 지원정보 테이블에 저장
                    ResumeInfo resumeInfo = ResumeInfo.builder()
                            .seeker(resultSeeker.get())
                            .integrated(false)
                            .build();
                    resumeInfoRepository.save(resumeInfo);

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

                    // 맞춤코드에 해당하는 테이블에 저장 후 지원정보 양식 테이블에 저장
                    for(String code : dto.getCodes()) {
                        if(code.equals("resume_001") && dto.getEducations() != null) {
                            // 학력 테이블에 저장 (조건 필요)
                            for(EducationCreateReq edu : dto.getEducations()) {
                                Education education = Education.builder()
                                        .resumeInfo(resumeInfo)
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
                        } else if(code.equals("resume_002") && dto.getPersonalHistories() != null) {
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

                        } else if(code.equals("resume_003") && dto.getInternsActivities() != null) {
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
                        } else if(code.equals("resume_004") && dto.getTrainings() != null) {
                            // 교육이수 테이블에 저장 (조건 필요) resume_004
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
                        } else if(code.equals("resume_005") && dto.getCertifications() != null) {
                            // 자격증 테이블에 저장 (조건 필요) resume_005
                            for(CertificationCreateReq c : dto.getCertifications()) {
                                Certification certification = Certification.builder()
                                        .resumeInfo(resumeInfo)
                                        .certName(c.getCertName())
                                        .organization(c.getOrganization())
                                        .takingAt(c.getTakingAt())
                                        .build();
                                certificationRepository.save(certification);
                            }
                        } else if(code.equals("resume_006") && dto.getAwards() != null) {
                            // 수상 테이블에 저장 (조건 필요) resume_006
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
                        } else if(code.equals("resume_007") && dto.getStudyingAbroads() != null) {
                            // 해외경험 테이블에 저장 (조건 필요) resume_007
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
                        } else if(code.equals("resume_008") && dto.getLanguages() != null) {
                            // 어학 테이블에 저장 (조건 필요) resume_008
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
                        } else if(code.equals("resume_009") && dto.getPortfolios() != null) {
                            // 포트폴리오 테이블에 저장 (조건 필요) resume_009
                            for(PortfolioCreateReq p : dto.getPortfolios()) {
                                Portfolio portfolio = Portfolio.builder()
                                        .resumeInfo(resumeInfo)
                                        .portfolioDiv(p.getPortfolioDiv())
                                        .portfolioType(p.getPortfolioType())
                                        .portfolioUrl(p.getPortfolioUrl())
                                        .build();
                                portfolioRepository.save(portfolio);
                            }
                        } else if(code.equals("resume_010") && dto.getPreferentialEmp() != null) {
                            // 취업우대&병역에 저장 (조건 필요) resume_010
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
                        } else if(code.equals("resume_011") && dto.getCustomLetters() != null) {
                            // 자기소개서 테이블에 저장 (조건 필요) resume_011
                            for(CustomLetterCreateReq cl : dto.getCustomLetters()) {
                                CustomLetter customLetter = CustomLetter.builder()
                                        .resumeInfo(resumeInfo)
                                        .title(cl.getTitle())
                                        .charNum(cl.getCharNum())
                                        .contents(cl.getContents())
                                        .build();
                                customLetterRepository.save(customLetter);
                            }
                        } else {
                            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL);
                        }

                        CustomResumeInfo customResumeInfo = CustomResumeInfo.builder()
                                .resumeInfo(resumeInfo)
                                .code(code)
                                .build();
                        customResumeInfoRepository.save(customResumeInfo);
                    }


                    // 공고지원서 테이블에 저장
                    Resume resume = Resume.builder()
                            .resumeTitle(dto.getResumeTitle())
                            .resumeInfo(resumeInfo)
                            .announcement(resultAnnouncement.get())
                            .seeker(resultSeeker.get())
                            .build();
                    resumeRepository.save(resume);


                    return ResumeSubmitRes.builder()
                            .resumeIdx(resume.getIdx())
                            .build();
                } else {
                    throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_RESUME_DUPLICATE);
                }

            } else {
                throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_ANNOUNCE);
            }
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER);
        }
    }


    @Transactional
    public ResumeReadSubmitInfoRes readSubmitInfo(CustomUserDetails customUserDetails, Long announcementIdx) throws BaseException {
        Long seekerIdx = customUserDetails.getIdx();
        // 지원자 테이블 조회
        Optional<Seeker> resultSeeker = seekerRepository.findBySeekerIdx(seekerIdx);
        if(resultSeeker.isPresent()) {
            // 공고 idx로 공고 조회
            Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnounceIdx(announcementIdx);
            if(resultAnnouncement.isPresent()) {
                ResumeReadSubmitInfoRes.ResumeReadSubmitInfoResBuilder responseBuilder = ResumeReadSubmitInfoRes.builder();
                // 지원서 맞춤 양식 테이블 조회
                List<CustomForm> resultCustomForms = customFormRepository.findAllByAnnouncementIdx(resultAnnouncement.get().getIdx());
                List<String> formCodes = new ArrayList<>();
                if(!resultCustomForms.isEmpty()) {
                    for(CustomForm cf : resultCustomForms) {
                        formCodes.add(cf.getCode());
                    }
                    responseBuilder.codes(formCodes);
                }
                if(formCodes.contains("resume_011")) { // 자기소개서
                    List<CustomLetterForm> resultCustomLetterForm = customLetterFormRepository.findAllByAnnouncementIdx(announcementIdx);
                    if(!resultCustomLetterForm.isEmpty()) {
                        // 자기소개서 맞춤 양식 테이블 조회 (공고 idx로)
                        List<CustomLetterFormReadRes> customLetterFormResList = new ArrayList<>();
                        for(CustomLetterForm customLetterForm : resultCustomLetterForm) {
                            CustomLetterFormReadRes customLetterFormRes = CustomLetterFormReadRes.builder()
                                    .title(customLetterForm.getTitle())
                                    .chatLimit(customLetterForm.getChatLimit())
                                    .build();
                            customLetterFormResList.add(customLetterFormRes);
                        }
                        responseBuilder.customLetterForms(customLetterFormResList);
                    }
                }
                // 지원정보 테이블 조회 (통합 지원서)
                Optional<ResumeInfo> resultResumeInfo = resumeInfoRepository.findBySeekerIdxAndIntegrated(seekerIdx, true);
                if(resultResumeInfo.isPresent()) {
                    // 인적사항
                    Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeInfoIdx(resultResumeInfo.get().getIdx());
                    if(resultPersonalInfo.isPresent()) {
                        PersonalInfo personalInfo = resultPersonalInfo.get();
                        PersonalInfoReadRes personalInfoRes = PersonalInfoReadRes.builder()
                                .name(personalInfo.getName())
                                .birth(personalInfo.getBirth())
                                .gender(personalInfo.getGender())
                                .email(personalInfo.getEmail())
                                .address(personalInfo.getAddress())
                                .phone(personalInfo.getPhone())
                                .tel(personalInfo.getTel())
                                .profileImg(personalInfo.getProfileImg())
                                .build();
                        responseBuilder.personalInfo(personalInfoRes);
                    }
                    if(formCodes.contains("resume_001")) { // 학력
                        List<Education> resultEducations = educationRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultEducations.isEmpty()) {
                            List<EducationReadRes> educationResList = new ArrayList<>();
                            for(Education education : resultEducations) {
                                EducationReadRes educationRes = EducationReadRes.builder()
                                        .schoolDiv(education.getSchoolDiv())
                                        .schoolName(education.getSchoolName())
                                        .enteredAt(education.getEnteredAt())
                                        .graduatedAt(education.getGraduatedAt())
                                        .graduationStatus(education.getGraduationStatus())
                                        .majorName(education.getMajorName())
                                        .grade(education.getGrade())
                                        .totalGrade(education.getTotalGrade())
                                        .transfer(education.getTransfer())
                                        .majorType(education.getMajorType())
                                        .otherMajor(education.getOtherMajor())
                                        .graduationWork(education.getGraduationWork())
                                        .degree(education.getDegree())
                                        .qualificationExam(education.getQualificationExam())
                                        .passedAt(education.getPassedAt())
                                        .build();
                                educationResList.add(educationRes);
                            }
                            responseBuilder.educations(educationResList);
                        }
                    }
                    if(formCodes.contains("resume_002")) { // 경력
                        List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultPersonalHistories.isEmpty()) {
                            List<PersonalHistoryReadRes> personalHistoryResList = new ArrayList<>();
                            for(PersonalHistory personalHistory : resultPersonalHistories) {
                                PersonalHistoryReadRes personalHistoryRes = PersonalHistoryReadRes.builder()
                                        .companyName(personalHistory.getCompanyName())
                                        .deptName(personalHistory.getDeptName())
                                        .enteredAt(personalHistory.getEnteredAt())
                                        .quitAt(personalHistory.getQuitAt())
                                        .empStatus(personalHistory.getEmpStatus())
                                        .position(personalHistory.getPosition())
                                        .job(personalHistory.getJob())
                                        .salary(personalHistory.getSalary())
                                        .work(personalHistory.getWork())
                                        .build();
                                personalHistoryResList.add(personalHistoryRes);
                            }
                            responseBuilder.personalHistories(personalHistoryResList);
                        }
                    }
                    if(formCodes.contains("resume_003")) { // 인턴&대외활동
                        List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultInternsActivities.isEmpty()) {
                            List<InternsActivityReadRes> internsActivityResList = new ArrayList<>();
                            for(InternsActivity internsActivity : resultInternsActivities) {
                                InternsActivityReadRes internsActivityRes = InternsActivityReadRes.builder()
                                        .activityDiv(internsActivity.getActivityDiv())
                                        .organization(internsActivity.getOrganization())
                                        .startAt(internsActivity.getStartAt())
                                        .endAt(internsActivity.getEndAt())
                                        .contents(internsActivity.getContents())
                                        .build();
                                internsActivityResList.add(internsActivityRes);
                            }
                            responseBuilder.internsActivities(internsActivityResList);
                        }
                    }
                    if(formCodes.contains("resume_004")) { // 교육이수
                        List<Training> resultTrainings = trainingRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultTrainings.isEmpty()) {
                            List<TrainingReadRes> trainingResList = new ArrayList<>();
                            for(Training training : resultTrainings) {
                                TrainingReadRes trainingRes = TrainingReadRes.builder()
                                        .trainingName(training.getTrainingName())
                                        .organization(training.getOrganization())
                                        .startAt(training.getStartAt())
                                        .endAt(training.getEndAt())
                                        .contents(training.getContents())
                                        .build();
                                trainingResList.add(trainingRes);
                            }
                            responseBuilder.trainings(trainingResList);
                        }
                    }
                    if(formCodes.contains("resume_005")) { // 자격증
                        List<Certification> resultCertifications = certificationRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultCertifications.isEmpty()) {
                            List<CertificationReadRes> certificationResList = new ArrayList<>();
                            for(Certification certification : resultCertifications) {
                                CertificationReadRes certificationRes = CertificationReadRes.builder()
                                        .certName(certification.getCertName())
                                        .organization(certification.getOrganization())
                                        .takingAt(certification.getTakingAt())
                                        .build();
                                certificationResList.add(certificationRes);
                            }
                            responseBuilder.certifications(certificationResList);
                        }
                    }
                    if(formCodes.contains("resume_006")) { // 수상
                        List<Award> resultAwards = awardRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultAwards.isEmpty()) {
                            List<AwardReadRes> awardResList = new ArrayList<>();
                            for(Award award : resultAwards) {
                                AwardReadRes awardRes = AwardReadRes.builder()
                                        .awardName(award.getAwardName())
                                        .contents(award.getContents())
                                        .organization(award.getOrganization())
                                        .year(award.getYear())
                                        .build();
                                awardResList.add(awardRes);
                            }
                            responseBuilder.awards(awardResList);
                        }
                    }
                    if(formCodes.contains("resume_007")) { // 해외경험
                        List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultStudyingAbroads.isEmpty()) {
                            List<StudyingAbroadReadRes> studyingAbroadResList = new ArrayList<>();
                            for(StudyingAbroad studyingAbroad : resultStudyingAbroads) {
                                StudyingAbroadReadRes studyingAbroadRes = StudyingAbroadReadRes.builder()
                                        .countryName(studyingAbroad.getCountryName())
                                        .startAt(studyingAbroad.getStartAt())
                                        .endAt(studyingAbroad.getEndAt())
                                        .contents(studyingAbroad.getContents())
                                        .build();
                                studyingAbroadResList.add(studyingAbroadRes);
                            }
                            responseBuilder.studyingAbroads(studyingAbroadResList);
                        }

                    }
                    if(formCodes.contains("resume_008")) { // 어학
                        List<Language> resultLanguages = languageRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultLanguages.isEmpty()) {
                            List<LanguageReadRes> languageResList = new ArrayList<>();
                            for(Language language : resultLanguages) {
                                LanguageReadRes languageRes = LanguageReadRes.builder()
                                        .testDiv(language.getTestDiv())
                                        .languageName(language.getLanguageName())
                                        .conversationLevel(language.getConversationLevel())
                                        .officialTest(language.getOfficialTest())
                                        .score(language.getScore())
                                        .takingAt(language.getTakingAt())
                                        .build();
                                languageResList.add(languageRes);
                            }
                            responseBuilder.languages(languageResList);
                        }
                    }
//                    if(formCodes.contains("resume_009")) { // 포트폴리오
//                        List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
//                        if(!resultPortfolios.isEmpty()) {
//                            List<PortfolioReadRes> portfolioResList = new ArrayList<>();
//                            for(Portfolio portfolio : resultPortfolios) {
//                                PortfolioReadRes portfolioRes = PortfolioReadRes.builder()
//                                        .portfolioDiv(portfolio.getPortfolioDiv())
//                                        .portfolioType(portfolio.getPortfolioType())
//                                        .portfolioUrl(portfolio.getPortfolioUrl())
//                                        .build();
//                                portfolioResList.add(portfolioRes);
//                            }
//                            responseBuilder.portfolios(portfolioResList);
//                        }
//                    }
                    if(formCodes.contains("resume_010")) { // 취업우대&병역
                        Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(resultPreferentialEmp.isPresent()) {
                            PreferentialEmp preferentialEmp = resultPreferentialEmp.get();
                            PreferentialEmpReadRes preferentialEmpRes = PreferentialEmpReadRes.builder()
                                    .veterans(preferentialEmp.getVeterans())
                                    .protection(preferentialEmp.getProtection())
                                    .subsidy(preferentialEmp.getSubsidy())
                                    .disability(preferentialEmp.getDisability())
                                    .disabilityDegree(preferentialEmp.getDisabilityDegree())
                                    .military(preferentialEmp.getMilitary())
                                    .militaryClass(preferentialEmp.getMilitaryClass())
                                    .militaryStart(preferentialEmp.getMilitaryStart())
                                    .militaryEnd(preferentialEmp.getMilitaryEnd())
                                    .militaryType(preferentialEmp.getMilitaryType())
                                    .militaryRank(preferentialEmp.getMilitaryRank())
                                    .build();
                            responseBuilder.preferentialEmp(preferentialEmpRes);
                        }
                    }


                    // 응답 dto 생성
                    return responseBuilder
                            .integrated(true)
                            .build();
                }
                return responseBuilder
                        .integrated(false)
                        .build();
            } else {
                throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_ANNOUNCE);
            }
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER);
        }

    }

    @Transactional
    public ResumeReadIntegratedRes readIntegrated(CustomUserDetails customUserDetails) throws BaseException {
        Long seekerIdx = customUserDetails.getIdx();
        // 지원자 테이블 조회
        Optional<Seeker> resultSeeker = seekerRepository.findBySeekerIdx(seekerIdx);
        if(resultSeeker.isPresent()) {
            // 지원정보 테이블 조회 (integration == 1)
            Optional<ResumeInfo> resultResumeInfo = resumeInfoRepository.findBySeekerIdxAndIntegrated(seekerIdx, true);
            if(resultResumeInfo.isPresent()) {
                ResumeReadIntegratedRes.ResumeReadIntegratedResBuilder responseBuilder = ResumeReadIntegratedRes.builder();

                // 인적사항 조회
                Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeInfoIdx(resultResumeInfo.get().getIdx());
                if(resultPersonalInfo.isPresent()) {
                    PersonalInfo personalInfo = resultPersonalInfo.get();
                    PersonalInfoReadRes personalInfoRes = PersonalInfoReadRes.builder()
                            .name(personalInfo.getName())
                            .birth(personalInfo.getBirth())
                            .gender(personalInfo.getGender())
                            .email(personalInfo.getEmail())
                            .address(personalInfo.getAddress())
                            .phone(personalInfo.getPhone())
                            .tel(personalInfo.getTel())
                            .profileImg(personalInfo.getProfileImg())
                            .build();
                    responseBuilder.personalInfo(personalInfoRes);
                }

                // 맞춤 지원정보 테이블 조회
                List<CustomResumeInfo> resultCustomResumeInfos = customResumeInfoRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                if(!resultCustomResumeInfos.isEmpty()) {

                    List<String> formCodes = new ArrayList<>();
                    for(CustomResumeInfo customResumeInfo : resultCustomResumeInfos) {
                        formCodes.add(customResumeInfo.getCode());
                    }

                    // 나머지 조회
                    if(formCodes.contains("resume_001")) { // 학력
                        List<Education> resultEducations = educationRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultEducations.isEmpty()) {
                            ArrayList<EducationReadRes> educationResList = new ArrayList<>();
                            for(Education education : resultEducations) {
                                EducationReadRes educationRes = EducationReadRes.builder()
                                        .schoolDiv(education.getSchoolDiv())
                                        .schoolName(education.getSchoolName())
                                        .enteredAt(education.getEnteredAt())
                                        .graduatedAt(education.getGraduatedAt())
                                        .graduationStatus(education.getGraduationStatus())
                                        .majorName(education.getMajorName())
                                        .grade(education.getGrade())
                                        .totalGrade(education.getTotalGrade())
                                        .transfer(education.getTransfer())
                                        .majorType(education.getMajorType())
                                        .otherMajor(education.getOtherMajor())
                                        .graduationWork(education.getGraduationWork())
                                        .degree(education.getDegree())
                                        .qualificationExam(education.getQualificationExam())
                                        .passedAt(education.getPassedAt())
                                        .build();
                                educationResList.add(educationRes);
                            }
                            responseBuilder.educations(educationResList);
                        }
                    }
                    if(formCodes.contains("resume_002")) { // 경력
                        List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultPersonalHistories.isEmpty()) {
                            ArrayList<PersonalHistoryReadRes> personalHistoryResList = new ArrayList<>();
                            for(PersonalHistory personalHistory : resultPersonalHistories) {
                                PersonalHistoryReadRes personalHistoryRes = PersonalHistoryReadRes.builder()
                                        .companyName(personalHistory.getCompanyName())
                                        .deptName(personalHistory.getDeptName())
                                        .enteredAt(personalHistory.getEnteredAt())
                                        .quitAt(personalHistory.getQuitAt())
                                        .empStatus(personalHistory.getEmpStatus())
                                        .position(personalHistory.getPosition())
                                        .job(personalHistory.getJob())
                                        .salary(personalHistory.getSalary())
                                        .work(personalHistory.getWork())
                                        .build();
                                personalHistoryResList.add(personalHistoryRes);
                            }
                            responseBuilder.personalHistories(personalHistoryResList);
                        }
                    }
                    if(formCodes.contains("resume_003")) { // 인턴&대외활동
                        List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultInternsActivities.isEmpty()) {
                            ArrayList<InternsActivityReadRes> internsActivityResList = new ArrayList<>();
                            for(InternsActivity internsActivity : resultInternsActivities) {
                                InternsActivityReadRes internsActivityRes = InternsActivityReadRes.builder()
                                        .activityDiv(internsActivity.getActivityDiv())
                                        .organization(internsActivity.getOrganization())
                                        .startAt(internsActivity.getStartAt())
                                        .endAt(internsActivity.getEndAt())
                                        .contents(internsActivity.getContents())
                                        .build();
                                internsActivityResList.add(internsActivityRes);
                            }
                            responseBuilder.internsActivities(internsActivityResList);
                        }
                    }
                    if(formCodes.contains("resume_004")) { // 교육이수
                        List<Training> resultTrainings = trainingRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultTrainings.isEmpty()) {
                            ArrayList<TrainingReadRes> trainingResList = new ArrayList<>();
                            for(Training training : resultTrainings) {
                                TrainingReadRes trainingRes = TrainingReadRes.builder()
                                        .trainingName(training.getTrainingName())
                                        .organization(training.getOrganization())
                                        .startAt(training.getStartAt())
                                        .endAt(training.getEndAt())
                                        .contents(training.getContents())
                                        .build();
                                trainingResList.add(trainingRes);
                            }
                            responseBuilder.trainings(trainingResList);
                        }
                    }
                    if(formCodes.contains("resume_005")) { // 자격증
                        List<Certification> resultCertifications = certificationRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultCertifications.isEmpty()) {
                            ArrayList<CertificationReadRes> certificationResList = new ArrayList<>();
                            for(Certification certification : resultCertifications) {
                                CertificationReadRes certificationRes = CertificationReadRes.builder()
                                        .certName(certification.getCertName())
                                        .organization(certification.getOrganization())
                                        .takingAt(certification.getTakingAt())
                                        .build();
                                certificationResList.add(certificationRes);
                            }
                            responseBuilder.certifications(certificationResList);
                        }
                    }
                    if(formCodes.contains("resume_006")) { // 수상
                        List<Award> resultAwards = awardRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultAwards.isEmpty()) {
                            ArrayList<AwardReadRes> awardResList = new ArrayList<>();
                            for(Award award : resultAwards) {
                                AwardReadRes awardRes = AwardReadRes.builder()
                                        .awardName(award.getAwardName())
                                        .contents(award.getContents())
                                        .organization(award.getOrganization())
                                        .year(award.getYear())
                                        .build();
                                awardResList.add(awardRes);
                            }
                            responseBuilder.awards(awardResList);
                        }
                    }
                    if(formCodes.contains("resume_007")) { // 해외경험
                        List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultStudyingAbroads.isEmpty()) {
                            ArrayList<StudyingAbroadReadRes> studyingAbroadResList = new ArrayList<>();
                            for(StudyingAbroad studyingAbroad : resultStudyingAbroads) {
                                StudyingAbroadReadRes studyingAbroadRes = StudyingAbroadReadRes.builder()
                                        .countryName(studyingAbroad.getCountryName())
                                        .startAt(studyingAbroad.getStartAt())
                                        .endAt(studyingAbroad.getEndAt())
                                        .contents(studyingAbroad.getContents())
                                        .build();
                                studyingAbroadResList.add(studyingAbroadRes);
                            }
                            responseBuilder.studyingAbroads(studyingAbroadResList);
                        }

                    }
                    if(formCodes.contains("resume_008")) { // 어학
                        List<Language> resultLanguages = languageRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultLanguages.isEmpty()) {
                            ArrayList<LanguageReadRes> languageResList = new ArrayList<>();
                            for(Language language : resultLanguages) {
                                LanguageReadRes languageRes = LanguageReadRes.builder()
                                        .testDiv(language.getTestDiv())
                                        .languageName(language.getLanguageName())
                                        .conversationLevel(language.getConversationLevel())
                                        .officialTest(language.getOfficialTest())
                                        .score(language.getScore())
                                        .takingAt(language.getTakingAt())
                                        .build();
                                languageResList.add(languageRes);
                            }
                            responseBuilder.languages(languageResList);
                        }
                    }
                    if(formCodes.contains("resume_009")) { // 포트폴리오
                        List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultPortfolios.isEmpty()) {
                            List<PortfolioReadRes> portfolioResList = new ArrayList<>();
                            for(Portfolio portfolio : resultPortfolios) {
                                PortfolioReadRes portfolioRes = PortfolioReadRes.builder()
                                        .portfolioDiv(portfolio.getPortfolioDiv())
                                        .portfolioType(portfolio.getPortfolioType())
                                        .portfolioUrl(portfolio.getPortfolioUrl())
                                        .build();
                                portfolioResList.add(portfolioRes);
                            }
                            responseBuilder.portfolios(portfolioResList);
                        }

                    }
                    if(formCodes.contains("resume_010")) { // 취업우대&병역
                        Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(resultPreferentialEmp.isPresent()) {
                            PreferentialEmp preferentialEmp = resultPreferentialEmp.get();
                            PreferentialEmpReadRes preferentialEmpRes = PreferentialEmpReadRes.builder()
                                    .veterans(preferentialEmp.getVeterans())
                                    .protection(preferentialEmp.getProtection())
                                    .subsidy(preferentialEmp.getSubsidy())
                                    .disability(preferentialEmp.getDisability())
                                    .disabilityDegree(preferentialEmp.getDisabilityDegree())
                                    .military(preferentialEmp.getMilitary())
                                    .militaryClass(preferentialEmp.getMilitaryClass())
                                    .militaryStart(preferentialEmp.getMilitaryStart())
                                    .militaryEnd(preferentialEmp.getMilitaryEnd())
                                    .militaryType(preferentialEmp.getMilitaryType())
                                    .militaryRank(preferentialEmp.getMilitaryRank())
                                    .build();
                            responseBuilder.preferentialEmp(preferentialEmpRes);
                        }
                    }
                    if(formCodes.contains("resume_011")) { // 자기소개서
                        List<CustomLetter> resultCustomLetter = customLetterRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if(!resultCustomLetter.isEmpty()) {
                            // 자기소개서 맞춤 양식 테이블 조회 (공고 idx로)
                            List<CustomLetterReadRes> customLetterResList = new ArrayList<>();
                            for(CustomLetter customLetter : resultCustomLetter) {
                                CustomLetterReadRes customLetterRes = CustomLetterReadRes.builder()
                                        .title(customLetter.getTitle())
                                        .charNum(customLetter.getCharNum())
                                        .contents(customLetter.getContents())
                                        .build();
                                customLetterResList.add(customLetterRes);
                            }
                            responseBuilder.customLetters(customLetterResList);
                        }
                    }
                    return responseBuilder
                            .codes(formCodes)
                            .build();
                }

                return responseBuilder
                        .build();

            } else {
                throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_INTEGRATED);
            }
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER);
        }

    }

    @Transactional
    public ResumeReadRes read(Long resumeIdx) throws BaseException {
        Optional<Resume> resultResume = resumeRepository.findByResumeIdx(resumeIdx);
        if(resultResume.isPresent()) {
            ResumeReadRes.ResumeReadResBuilder responseBuilder = ResumeReadRes.builder();

            responseBuilder.resumeTitle(resultResume.get().getResumeTitle());
            responseBuilder.resumedAt(resultResume.get().getResumedAt());
            responseBuilder.seekerIdx(resultResume.get().getSeeker().getIdx());
            responseBuilder.announcementIdx(resultResume.get().getAnnouncement().getIdx());
            // total_process 테이블에서 조회하기
            Optional<TotalProcess> resultTotalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(
                    resultResume.get().getAnnouncement().getIdx(), resultResume.get().getSeeker().getIdx()
            );
            if(resultTotalProcess.isPresent()) {
                responseBuilder.docPassed(resultTotalProcess.get().getResumeResult());
            } else {
                responseBuilder.docPassed(null);
            }
            // 지원정보 테이블 조회
            Optional<ResumeInfo> resultResumeInfo = resumeInfoRepository.findByResumeInfoIdx(resultResume.get().getResumeInfo().getIdx());
            if(resultResumeInfo.isPresent()) {

                // 인적사항 조회
                Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeInfoIdx(resultResumeInfo.get().getIdx());
                if(resultPersonalInfo.isPresent()) {
                    PersonalInfo personalInfo = resultPersonalInfo.get();
                    PersonalInfoReadRes personalInfoRes = PersonalInfoReadRes.builder()
                            .name(personalInfo.getName())
                            .birth(personalInfo.getBirth())
                            .gender(personalInfo.getGender())
                            .email(personalInfo.getEmail())
                            .address(personalInfo.getAddress())
                            .phone(personalInfo.getPhone())
                            .tel(personalInfo.getTel())
                            .profileImg(personalInfo.getProfileImg())
                            .build();
                    responseBuilder.personalInfo(personalInfoRes);
                }

                // 맞춤 지원정보 테이블 조회
                List<CustomResumeInfo> resultCustomResumeInfos = customResumeInfoRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                if(!resultCustomResumeInfos.isEmpty()) {

                    List<String> formCodes = new ArrayList<>();
                    for (CustomResumeInfo customResumeInfo : resultCustomResumeInfos) {
                        formCodes.add(customResumeInfo.getCode());
                    }

                    // 나머지 조회
                    if (formCodes.contains("resume_001")) { // 학력
                        List<Education> resultEducations = educationRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultEducations.isEmpty()) {
                            ArrayList<EducationReadRes> educationResList = new ArrayList<>();
                            for (Education education : resultEducations) {
                                EducationReadRes educationRes = EducationReadRes.builder()
                                        .schoolDiv(education.getSchoolDiv())
                                        .schoolName(education.getSchoolName())
                                        .enteredAt(education.getEnteredAt())
                                        .graduatedAt(education.getGraduatedAt())
                                        .graduationStatus(education.getGraduationStatus())
                                        .majorName(education.getMajorName())
                                        .grade(education.getGrade())
                                        .totalGrade(education.getTotalGrade())
                                        .transfer(education.getTransfer())
                                        .majorType(education.getMajorType())
                                        .otherMajor(education.getOtherMajor())
                                        .graduationWork(education.getGraduationWork())
                                        .degree(education.getDegree())
                                        .qualificationExam(education.getQualificationExam())
                                        .passedAt(education.getPassedAt())
                                        .build();
                                educationResList.add(educationRes);
                            }
                            responseBuilder.educations(educationResList);
                        }
                    }
                    if (formCodes.contains("resume_002")) { // 경력
                        List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultPersonalHistories.isEmpty()) {
                            ArrayList<PersonalHistoryReadRes> personalHistoryResList = new ArrayList<>();
                            for (PersonalHistory personalHistory : resultPersonalHistories) {
                                PersonalHistoryReadRes personalHistoryRes = PersonalHistoryReadRes.builder()
                                        .companyName(personalHistory.getCompanyName())
                                        .deptName(personalHistory.getDeptName())
                                        .enteredAt(personalHistory.getEnteredAt())
                                        .quitAt(personalHistory.getQuitAt())
                                        .empStatus(personalHistory.getEmpStatus())
                                        .position(personalHistory.getPosition())
                                        .job(personalHistory.getJob())
                                        .salary(personalHistory.getSalary())
                                        .work(personalHistory.getWork())
                                        .build();
                                personalHistoryResList.add(personalHistoryRes);
                            }
                            responseBuilder.personalHistories(personalHistoryResList);
                        }
                    }
                    if (formCodes.contains("resume_003")) { // 인턴&대외활동
                        List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultInternsActivities.isEmpty()) {
                            ArrayList<InternsActivityReadRes> internsActivityResList = new ArrayList<>();
                            for (InternsActivity internsActivity : resultInternsActivities) {
                                InternsActivityReadRes internsActivityRes = InternsActivityReadRes.builder()
                                        .activityDiv(internsActivity.getActivityDiv())
                                        .organization(internsActivity.getOrganization())
                                        .startAt(internsActivity.getStartAt())
                                        .endAt(internsActivity.getEndAt())
                                        .contents(internsActivity.getContents())
                                        .build();
                                internsActivityResList.add(internsActivityRes);
                            }
                            responseBuilder.internsActivities(internsActivityResList);
                        }
                    }
                    if (formCodes.contains("resume_004")) { // 교육이수
                        List<Training> resultTrainings = trainingRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultTrainings.isEmpty()) {
                            ArrayList<TrainingReadRes> trainingResList = new ArrayList<>();
                            for (Training training : resultTrainings) {
                                TrainingReadRes trainingRes = TrainingReadRes.builder()
                                        .trainingName(training.getTrainingName())
                                        .organization(training.getOrganization())
                                        .startAt(training.getStartAt())
                                        .endAt(training.getEndAt())
                                        .contents(training.getContents())
                                        .build();
                                trainingResList.add(trainingRes);
                            }
                            responseBuilder.trainings(trainingResList);
                        }
                    }
                    if (formCodes.contains("resume_005")) { // 자격증
                        List<Certification> resultCertifications = certificationRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultCertifications.isEmpty()) {
                            ArrayList<CertificationReadRes> certificationResList = new ArrayList<>();
                            for (Certification certification : resultCertifications) {
                                CertificationReadRes certificationRes = CertificationReadRes.builder()
                                        .certName(certification.getCertName())
                                        .organization(certification.getOrganization())
                                        .takingAt(certification.getTakingAt())
                                        .build();
                                certificationResList.add(certificationRes);
                            }
                            responseBuilder.certifications(certificationResList);
                        }
                    }
                    if (formCodes.contains("resume_006")) { // 수상
                        List<Award> resultAwards = awardRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultAwards.isEmpty()) {
                            ArrayList<AwardReadRes> awardResList = new ArrayList<>();
                            for (Award award : resultAwards) {
                                AwardReadRes awardRes = AwardReadRes.builder()
                                        .awardName(award.getAwardName())
                                        .contents(award.getContents())
                                        .organization(award.getOrganization())
                                        .year(award.getYear())
                                        .build();
                                awardResList.add(awardRes);
                            }
                            responseBuilder.awards(awardResList);
                        }
                    }
                    if (formCodes.contains("resume_007")) { // 해외경험
                        List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultStudyingAbroads.isEmpty()) {
                            ArrayList<StudyingAbroadReadRes> studyingAbroadResList = new ArrayList<>();
                            for (StudyingAbroad studyingAbroad : resultStudyingAbroads) {
                                StudyingAbroadReadRes studyingAbroadRes = StudyingAbroadReadRes.builder()
                                        .countryName(studyingAbroad.getCountryName())
                                        .startAt(studyingAbroad.getStartAt())
                                        .endAt(studyingAbroad.getEndAt())
                                        .contents(studyingAbroad.getContents())
                                        .build();
                                studyingAbroadResList.add(studyingAbroadRes);
                            }
                            responseBuilder.studyingAbroads(studyingAbroadResList);
                        }

                    }
                    if (formCodes.contains("resume_008")) { // 어학
                        List<Language> resultLanguages = languageRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultLanguages.isEmpty()) {
                            ArrayList<LanguageReadRes> languageResList = new ArrayList<>();
                            for (Language language : resultLanguages) {
                                LanguageReadRes languageRes = LanguageReadRes.builder()
                                        .testDiv(language.getTestDiv())
                                        .languageName(language.getLanguageName())
                                        .conversationLevel(language.getConversationLevel())
                                        .officialTest(language.getOfficialTest())
                                        .score(language.getScore())
                                        .takingAt(language.getTakingAt())
                                        .build();
                                languageResList.add(languageRes);
                            }
                            responseBuilder.languages(languageResList);
                        }
                    }
                    if (formCodes.contains("resume_009")) { // 포트폴리오
                        List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultPortfolios.isEmpty()) {
                            List<PortfolioReadRes> portfolioResList = new ArrayList<>();
                            for (Portfolio portfolio : resultPortfolios) {
                                PortfolioReadRes portfolioRes = PortfolioReadRes.builder()
                                        .portfolioDiv(portfolio.getPortfolioDiv())
                                        .portfolioType(portfolio.getPortfolioType())
                                        .portfolioUrl(portfolio.getPortfolioUrl())
                                        .build();
                                portfolioResList.add(portfolioRes);
                            }
                            responseBuilder.portfolios(portfolioResList);
                        }

                    }
                    if (formCodes.contains("resume_010")) { // 취업우대&병역
                        Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (resultPreferentialEmp.isPresent()) {
                            PreferentialEmp preferentialEmp = resultPreferentialEmp.get();
                            PreferentialEmpReadRes preferentialEmpRes = PreferentialEmpReadRes.builder()
                                    .veterans(preferentialEmp.getVeterans())
                                    .protection(preferentialEmp.getProtection())
                                    .subsidy(preferentialEmp.getSubsidy())
                                    .disability(preferentialEmp.getDisability())
                                    .disabilityDegree(preferentialEmp.getDisabilityDegree())
                                    .military(preferentialEmp.getMilitary())
                                    .militaryClass(preferentialEmp.getMilitaryClass())
                                    .militaryStart(preferentialEmp.getMilitaryStart())
                                    .militaryEnd(preferentialEmp.getMilitaryEnd())
                                    .militaryType(preferentialEmp.getMilitaryType())
                                    .militaryRank(preferentialEmp.getMilitaryRank())
                                    .build();
                            responseBuilder.preferentialEmp(preferentialEmpRes);
                        }
                    }
                    if (formCodes.contains("resume_011")) { // 자기소개서
                        List<CustomLetter> resultCustomLetter = customLetterRepository.findAllByResumeInfoIdx(resultResumeInfo.get().getIdx());
                        if (!resultCustomLetter.isEmpty()) {
                            // 자기소개서 맞춤 양식 테이블 조회 (공고 idx로)
                            List<CustomLetterReadRes> customLetterResList = new ArrayList<>();
                            for (CustomLetter customLetter : resultCustomLetter) {
                                CustomLetterReadRes customLetterRes = CustomLetterReadRes.builder()
                                        .title(customLetter.getTitle())
                                        .charNum(customLetter.getCharNum())
                                        .contents(customLetter.getContents())
                                        .build();
                                customLetterResList.add(customLetterRes);
                            }
                            responseBuilder.customLetters(customLetterResList);
                        }
                    }
                    return responseBuilder
                            .codes(formCodes)
                            .build();

                }
                return responseBuilder
                        .build();
            } else {
                throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_RESUME_INFO);
            }

        } else {
            throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_RESUME);
        }

    }

    @Transactional
    public List<ResumeReadAllRes> readAll(CustomUserDetails customUserDetails) throws BaseException {
        Long seekerIdx = customUserDetails.getIdx();
        // 지원자 테이블 조회
        Optional<Seeker> resultSeeker = seekerRepository.findBySeekerIdx(seekerIdx);
        if(resultSeeker.isPresent()) {
            // 지원자 idx로 공고지원서 테이블 조회
            List<Resume> resultResumes = resumeRepository.findAllBySeekerIdx(seekerIdx);
            if(!resultResumes.isEmpty()) {
                List<ResumeReadAllRes> resumeReadAllResList = new ArrayList<>();
                for(Resume resume : resultResumes) {
                    // 공고 idx로 테이블 조회
                    Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnounceIdx(resume.getAnnouncement().getIdx());
                    if(resultAnnouncement.isPresent()) {
                        // 공고 테이블의 채용담당자 idx로 기업명 조회
                        Optional<Company> resultCompany = companyRepository.findByRecruiterIdx(resultAnnouncement.get().getRecruiter().getIdx());
                        if(resultCompany.isPresent()) {
                            // total_process 테이블에서 조회하기
                            Optional<TotalProcess> resultTotalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(
                                    resume.getAnnouncement().getIdx(), resume.getSeeker().getIdx()
                            );
                            if (resultTotalProcess.isPresent()) {
                                TotalProcess totalProcess = resultTotalProcess.get();
                                ResumeReadAllRes resumeReadAllRes = ResumeReadAllRes.builder()
                                        .resumeIdx(resume.getIdx())
                                        .resumeTitle(resume.getResumeTitle())
                                        .resumedAt(resume.getResumedAt())
                                        .announcementIdx(resultAnnouncement.get().getIdx())
                                        .announcementTitle(resultAnnouncement.get().getTitle())
                                        .announcementStart(resultAnnouncement.get().getAnnouncementStart())
                                        .announcementEnd(resultAnnouncement.get().getAnnouncementEnd())
                                        .companyName(resultCompany.get().getName())
                                        .resumeResult(totalProcess.getResumeResult())
                                        .interviewOneResult(totalProcess.getInterviewOneResult())
                                        .interviewTwoResult(totalProcess.getInterviewTwoResult())
                                        .finalResult(totalProcess.getFinalResult())
                                        .build();
                                resumeReadAllResList.add(resumeReadAllRes);
                            } else {
                                ResumeReadAllRes resumeReadAllRes = ResumeReadAllRes.builder()
                                        .resumeIdx(resume.getIdx())
                                        .resumeTitle(resume.getResumeTitle())
                                        .resumedAt(resume.getResumedAt())
                                        .announcementIdx(resultAnnouncement.get().getIdx())
                                        .announcementTitle(resultAnnouncement.get().getTitle())
                                        .announcementStart(resultAnnouncement.get().getAnnouncementStart())
                                        .announcementEnd(resultAnnouncement.get().getAnnouncementEnd())
                                        .companyName(resultCompany.get().getName())
                                        .resumeResult(null)
                                        .interviewOneResult(null)
                                        .interviewTwoResult(null)
                                        .finalResult(null)
                                        .build();
                                resumeReadAllResList.add(resumeReadAllRes);
                            }
                        } else {
                            throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_COMPANY_NOT_FOUND);
                        }
                    } else {
                        throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_ANNOUNCE);
                    }
                }
                return resumeReadAllResList;
            } else {
                throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_RESUME_NOT_FOUND);
            }
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER);
        }
    }

    public List<ResumeReadAllRecruiterRes> readAllRecruiter(CustomUserDetails customUserDetails, Long announcementIdx) throws BaseException {
        Long recruiterIdx = customUserDetails.getIdx();
        // 채용담당자 테이블 조회
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isPresent()) {
            // 공고 테이블 조회 , 공고 idx가 채용담당자가 등록한 공고가 맞는지
            Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnounceIdx(announcementIdx);
            if(resultAnnouncement.isPresent()) {
                if(!resultAnnouncement.get().getRecruiter().getIdx().equals(recruiterIdx)) {
                    throw new BaseException(BaseResponseMessage.ACCESS_DENIED);
                }
                // 공고지원서 테이블 조회
                List<Resume> resultResumes = resumeRepository.findAllByAnnouncementIdx(announcementIdx);
                if(!resultResumes.isEmpty()) {
                    List<ResumeReadAllRecruiterRes> resumeReadAllRecruiterResList = new ArrayList<>();
                    for(Resume resume : resultResumes) {
                        // total_process 테이블에서 조회하기
                        Optional<TotalProcess> resultTotalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(
                                resume.getAnnouncement().getIdx(), resume.getSeeker().getIdx()
                        );
                        if (resultTotalProcess.isPresent()) {
                            TotalProcess totalProcess = resultTotalProcess.get();
                            ResumeReadAllRecruiterRes resumeReadAllRecruiterRes = ResumeReadAllRecruiterRes.builder()
                                    .resumeIdx(resume.getIdx())
                                    .resumeTitle(resume.getResumeTitle())
                                    .resumedAt(resume.getResumedAt())
                                    .resumeResult(totalProcess.getResumeResult())
                                    .interviewOneResult(totalProcess.getInterviewOneResult())
                                    .interviewTwoResult(totalProcess.getInterviewTwoResult())
                                    .finalResult(totalProcess.getFinalResult())
                                    .seekerName(resume.getSeeker().getName())
                                    .build();
                            resumeReadAllRecruiterResList.add(resumeReadAllRecruiterRes);
                        } else {
                            ResumeReadAllRecruiterRes resumeReadAllRecruiterRes = ResumeReadAllRecruiterRes.builder()
                                    .resumeIdx(resume.getIdx())
                                    .resumeTitle(resume.getResumeTitle())
                                    .resumedAt(resume.getResumedAt())
                                    .resumeResult(null)
                                    .interviewOneResult(null)
                                    .interviewTwoResult(null)
                                    .finalResult(null)
                                    .seekerName(resume.getSeeker().getName())
                                    .build();
                            resumeReadAllRecruiterResList.add(resumeReadAllRecruiterRes);
                        }

                    }
                    return resumeReadAllRecruiterResList;
                } else {
                    throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_RESUME);
                }
            } else {
                throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_ANNOUNCE);
            }
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER);
        }
    }
}
