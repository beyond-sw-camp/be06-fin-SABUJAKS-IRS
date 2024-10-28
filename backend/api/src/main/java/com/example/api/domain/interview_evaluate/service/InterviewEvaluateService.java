package com.example.api.domain.interview_evaluate.service;

import com.example.api.domain.interview_evaluate.model.request.InterviewEvaluateCreateReq;
import com.example.api.domain.interview_evaluate.model.request.InterviewEvaluateFormCreateReq;
import com.example.api.domain.interview_evaluate.model.response.*;
import com.example.api.domain.resume.model.response.*;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.announcement.repository.AnnouncementRepository;
import com.example.common.domain.announcement.repository.CustomLetterFormRepository;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.auth.repository.SeekerRepository;
import com.example.common.domain.interview_evaluate.model.entity.InterviewEvaluate;
import com.example.common.domain.interview_evaluate.model.entity.InterviewEvaluateForm;
import com.example.common.domain.interview_evaluate.model.entity.InterviewEvaluateResult;
import com.example.common.domain.interview_evaluate.repository.InterviewEvaluateFormRepository;
import com.example.common.domain.interview_evaluate.repository.InterviewEvaluateRepository;
import com.example.common.domain.interview_evaluate.repository.InterviewEvaluateResultRepository;
import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.interview_schedule.repository.InterviewParticipateRepository;
import com.example.common.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.example.common.domain.resume.model.entity.*;
import com.example.common.domain.resume.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InterviewEvaluateService {
    private final SeekerRepository seekerRepository;
    private final InterviewEvaluateRepository interviewEvaluateRepository;
    private final InterviewEvaluateFormRepository interviewEvaluateFormRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;
    private final AnnouncementRepository announcementRepository;
    private final ResumeRepository resumeRepository;
    private final CustomResumeInfoRepository customResumeInfoRepository;
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
    private final PortfolioRepository portfolioRepository;
    private final CustomLetterRepository customLetterRepository;
    private final CustomLetterFormRepository customLetterFormRepository;
    private final InterviewEvaluateResultRepository interviewEvaluateResultRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;

    public InterviewEvaluateService(SeekerRepository seekerRepository, InterviewEvaluateRepository interviewEvaluateRepository, InterviewEvaluateFormRepository interviewEvaluateFormRepository, InterviewParticipateRepository interviewParticipateRepository, AnnouncementRepository announcementRepository, ResumeRepository resumeRepository, CustomResumeInfoRepository customResumeInfoRepository, PersonalInfoRepository personalInfoRepository, PreferentialEmpRepository preferentialEmpRepository, EducationRepository educationRepository, PersonalHistoryRepository personalHistoryRepository, InternActivitiesRepository internActivitiesRepository, StudyingAboardRepository studyingAboardRepository, LanguageRepository languageRepository, CertificationRepository certificationRepository, TrainingRepository trainingRepository, AwardRepository awardRepository, PortfolioRepository portfolioRepository, CustomLetterRepository customLetterRepository, CustomLetterFormRepository customLetterFormRepository, InterviewEvaluateResultRepository interviewEvaluateResultRepository, InterviewScheduleRepository interviewScheduleRepository) {
        this.seekerRepository = seekerRepository;
        this.interviewEvaluateRepository = interviewEvaluateRepository;
        this.interviewEvaluateFormRepository = interviewEvaluateFormRepository;
        this.interviewParticipateRepository = interviewParticipateRepository;
        this.announcementRepository = announcementRepository;
        this.resumeRepository = resumeRepository;
        this.customResumeInfoRepository = customResumeInfoRepository;
        this.personalInfoRepository = personalInfoRepository;
        this.preferentialEmpRepository = preferentialEmpRepository;
        this.educationRepository = educationRepository;
        this.personalHistoryRepository = personalHistoryRepository;
        this.internActivitiesRepository = internActivitiesRepository;
        this.studyingAboardRepository = studyingAboardRepository;
        this.languageRepository = languageRepository;
        this.certificationRepository = certificationRepository;
        this.trainingRepository = trainingRepository;
        this.awardRepository = awardRepository;
        this.portfolioRepository = portfolioRepository;
        this.customLetterRepository = customLetterRepository;
        this.customLetterFormRepository = customLetterFormRepository;
        this.interviewEvaluateResultRepository = interviewEvaluateResultRepository;
        this.interviewScheduleRepository = interviewScheduleRepository;
    }

    public InterviewEvaluateFormCreateRes createForm (CustomUserDetails customUserDetails, InterviewEvaluateFormCreateReq dto) throws BaseException {
        Announcement announcement = announcementRepository.findByAnnounceIdx(dto.getAnnounceIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL));
        if(!Objects.equals(announcement.getRecruiter().getEmail(), customUserDetails.getEmail())){
            throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FORM_FAIL_INVALID_REQUEST);
        }
        Optional<InterviewEvaluateForm> result = interviewEvaluateFormRepository.findByAnnounceIdx(dto.getAnnounceIdx());
        if(result.isEmpty()){
            InterviewEvaluateForm interviewEvaluateForm = InterviewEvaluateForm.builder()
                    .announcement(announcement)
                    .q1(dto.getQ1())
                    .q2(dto.getQ2())
                    .q3(dto.getQ3())
                    .q4(dto.getQ4())
                    .q5(dto.getQ5())
                    .q6(dto.getQ6())
                    .q7(dto.getQ7())
                    .q8(dto.getQ8())
                    .q9(dto.getQ9())
                    .q10(dto.getQ10())
                    .build();
            interviewEvaluateFormRepository.save(interviewEvaluateForm);
            return InterviewEvaluateFormCreateRes.builder()
                    .idx(interviewEvaluateForm.getIdx())
                    .build();
        } else {
            InterviewEvaluateForm interviewEvaluateForm = result.get();
            interviewEvaluateForm.setQ1(dto.getQ1());
            interviewEvaluateForm.setQ2(dto.getQ2());
            interviewEvaluateForm.setQ3(dto.getQ3());
            interviewEvaluateForm.setQ4(dto.getQ4());
            interviewEvaluateForm.setQ5(dto.getQ5());
            interviewEvaluateForm.setQ6(dto.getQ6());
            interviewEvaluateForm.setQ7(dto.getQ7());
            interviewEvaluateForm.setQ8(dto.getQ8());
            interviewEvaluateForm.setQ9(dto.getQ9());
            interviewEvaluateForm.setQ10(dto.getQ10());
            interviewEvaluateFormRepository.save(interviewEvaluateForm);
            return InterviewEvaluateFormCreateRes.builder()
                    .idx(interviewEvaluateForm.getIdx())
                    .build();
        }
    }

    public InterviewEvaluateFormReadRes searchForm(CustomUserDetails customUserDetails, String announcementUUID, String interviewScheduleUUID) throws  BaseException {
        if(Objects.equals(customUserDetails.getRole(), "ROLE_RECRUITER")){
            InterviewEvaluateForm interviewEvaluateForm = interviewEvaluateFormRepository.findByAnnouncementUUID(announcementUUID)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_IS_NOT_EXIST));
            if(!Objects.equals(interviewEvaluateForm.getAnnouncement().getRecruiter().getEmail(), customUserDetails.getEmail())){
                throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS);
            };
            return InterviewEvaluateFormReadRes.builder()
                    .q1(interviewEvaluateForm.getQ1())
                    .q2(interviewEvaluateForm.getQ2())
                    .q3(interviewEvaluateForm.getQ3())
                    .q4(interviewEvaluateForm.getQ4())
                    .q5(interviewEvaluateForm.getQ5())
                    .q6(interviewEvaluateForm.getQ6())
                    .q7(interviewEvaluateForm.getQ7())
                    .q8(interviewEvaluateForm.getQ8())
                    .q9(interviewEvaluateForm.getQ9())
                    .q10(interviewEvaluateForm.getQ10())
                    .build();
        } else if(Objects.equals(customUserDetails.getRole(), "ROLE_ESTIMATOR")) {
            if(interviewParticipateRepository.findFirstByEstimatorIdxAndInterviewScheduleUUID(customUserDetails.getIdx(), interviewScheduleUUID).isEmpty()){
                throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS);
            }
            InterviewEvaluateForm interviewEvaluateForm = interviewEvaluateFormRepository.findByAnnouncementUUID(announcementUUID)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_IS_NOT_EXIST));
            return InterviewEvaluateFormReadRes.builder()
                    .q1(interviewEvaluateForm.getQ1())
                    .q2(interviewEvaluateForm.getQ2())
                    .q3(interviewEvaluateForm.getQ3())
                    .q4(interviewEvaluateForm.getQ4())
                    .q5(interviewEvaluateForm.getQ5())
                    .q6(interviewEvaluateForm.getQ6())
                    .q7(interviewEvaluateForm.getQ7())
                    .q8(interviewEvaluateForm.getQ8())
                    .q9(interviewEvaluateForm.getQ9())
                    .q10(interviewEvaluateForm.getQ10())
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS);
        }
    }

    @Transactional
    public InterviewEvaluateReadAllResumeInfo readAllResumeInfo(CustomUserDetails customUserDetails, String announcementUUID, String interviewScheduleUUID) throws BaseException{
        List<InterviewParticipate> interviewParticipateList = interviewParticipateRepository
                .findAllByEstimatorIdxAndInterviewScheduleUUID(customUserDetails.getIdx(), interviewScheduleUUID)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS));
        Map<Long, InterviewEvaluateReadResumeInfoRes> interviewEvaluateReadResumeInfoResMap = new HashMap();
        for(InterviewParticipate interviewParticipate:interviewParticipateList){
            Seeker seeker = interviewParticipate.getSeeker();
            Announcement announcement = announcementRepository.findByAnnouncementUUID(announcementUUID)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_ANNOUNCE));
            Resume resume = resumeRepository.findByAnnouncementIdxAndSeekerIdx(announcement.getIdx(), seeker.getIdx())
                    .orElseThrow(()-> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_RESUME_FAIL_NOT_FOUND));
            List<CustomResumeInfo> customResumeInfoList = customResumeInfoRepository.findAllByResumeInfoIdx(resume.getResumeInfo().getIdx());
            InterviewEvaluateReadResumeInfoRes.InterviewEvaluateReadResumeInfoResBuilder responseBuilder = InterviewEvaluateReadResumeInfoRes.builder();
            Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeInfoIdx(resume.getResumeInfo().getIdx());
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
            for(CustomResumeInfo customResumeInfo : customResumeInfoList) {
                if(Objects.equals(customResumeInfo.getCode(), "resume_001")) { // 학력
                    List<Education> resultEducations = educationRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if(Objects.equals(customResumeInfo.getCode(), "resume_002")) { // 경력
                    List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if(Objects.equals(customResumeInfo.getCode(), "resume_003")) { // 인턴&대외활동
                    List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if(Objects.equals(customResumeInfo.getCode(), "resume_004")) { // 교육이수
                    List<Training> resultTrainings = trainingRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if((Objects.equals(customResumeInfo.getCode(), "resume_005"))) { // 자격증
                    List<Certification> resultCertifications = certificationRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if((Objects.equals(customResumeInfo.getCode(), "resume_006"))) { // 수상
                    List<Award> resultAwards = awardRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if((Objects.equals(customResumeInfo.getCode(), "resume_007"))) { // 해외경험
                    List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if((Objects.equals(customResumeInfo.getCode(), "resume_008"))) { // 어학
                    List<Language> resultLanguages = languageRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if((Objects.equals(customResumeInfo.getCode(), "resume_009"))) { // 포트폴리오
                    List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if((Objects.equals(customResumeInfo.getCode(), "resume_010"))) { // 취업우대&병역
                    Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
                if((Objects.equals(customResumeInfo.getCode(), "resume_011"))) { // 자기소개서
                    List<CustomLetter> resultCustomLetter = customLetterRepository.findAllByResumeInfoIdx(customResumeInfo.getResumeInfo().getIdx());
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
            }
            interviewEvaluateReadResumeInfoResMap.put(seeker.getIdx(), responseBuilder.build());
        }
        return InterviewEvaluateReadAllResumeInfo.builder()
                .interviewEvaluateReadResumeInfoResMap(interviewEvaluateReadResumeInfoResMap)
                .build();
    }

    @Transactional
    public InterviewEvaluateCreateRes createEvaluate(CustomUserDetails customUserDetails, InterviewEvaluateCreateReq dto) throws BaseException{
        InterviewParticipate interviewParticipate = interviewParticipateRepository
                .findBySeekerEmailAndEstimatorIdxAndInterviewScheduleUUID(dto.getUserEmail(), customUserDetails.getIdx(), dto.getVideoInterviewUUID())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FAIL_NOT_FOUND_SCHEDULE));
        InterviewEvaluateForm interviewEvaluateForm = interviewEvaluateFormRepository.findByAnnouncementUUID(dto.getAnnouncementUUID())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FAIL_INVALID_FORM));
        Optional<InterviewEvaluate> result = interviewEvaluateRepository.findByInterviewParticipateIdx(interviewParticipate.getIdx());
        if(result.isPresent()){
            InterviewEvaluate interviewEvaluate = result.get();
            InterviewEvaluateResult interviewEvaluateResult = InterviewEvaluateResult.builder()
                    .idx(interviewEvaluate.getInterviewEvaluateResult().getIdx())
                    .r1(dto.getScores().get(1))
                    .r2(dto.getScores().get(2))
                    .r3(dto.getScores().get(3))
                    .r4(dto.getScores().get(4))
                    .r5(dto.getScores().get(5))
                    .r6(dto.getScores().get(6))
                    .r7(dto.getScores().get(7))
                    .r8(dto.getScores().get(8))
                    .r9(dto.getScores().get(9))
                    .r10(dto.getScores().get(10))
                    .build();
            interviewEvaluateResultRepository.save(interviewEvaluateResult);
            InterviewEvaluate newInterviewEvaluate = InterviewEvaluate.builder()
                    .idx(interviewEvaluate.getIdx())
                    .totalScore(dto.getTotalScore())
                    .comments(dto.getComments())
                    .interviewEvaluateForm(interviewEvaluateForm)
                    .interviewParticipate(interviewParticipate)
                    .interviewEvaluateResult(interviewEvaluateResult)
                    .build();
            interviewEvaluateRepository.save(newInterviewEvaluate);
            return InterviewEvaluateCreateRes.builder()
                    .idx(newInterviewEvaluate.getIdx())
                    .build();
        } else {
            InterviewEvaluateResult interviewEvaluateResult = InterviewEvaluateResult.builder()
                    .r1(dto.getScores().get(1))
                    .r2(dto.getScores().get(2))
                    .r3(dto.getScores().get(3))
                    .r4(dto.getScores().get(4))
                    .r5(dto.getScores().get(5))
                    .r6(dto.getScores().get(6))
                    .r7(dto.getScores().get(7))
                    .r8(dto.getScores().get(8))
                    .r9(dto.getScores().get(9))
                    .r10(dto.getScores().get(10))
                    .build();
            interviewEvaluateResultRepository.save(interviewEvaluateResult);
            InterviewEvaluate interviewEvaluate = InterviewEvaluate.builder()
                    .totalScore(dto.getTotalScore())
                    .comments(dto.getComments())
                    .interviewEvaluateForm(interviewEvaluateForm)
                    .interviewParticipate(interviewParticipate)
                    .interviewEvaluateResult(interviewEvaluateResult)
                    .build();
            interviewEvaluateRepository.save(interviewEvaluate);
            return InterviewEvaluateCreateRes.builder()
                    .idx(interviewEvaluate.getIdx())
                    .build();
        }
    }

    public InterviewEvaluateReadAllRes readAllEvaluate(CustomUserDetails customUserDetails, Long announcementIdx, Integer interviewNum) throws BaseException {
        Announcement announcement = announcementRepository.findByAnnounceIdx(announcementIdx)
        .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL_NOT_FOUND));
        if (!Objects.equals(announcement.getRecruiter().getEmail(), customUserDetails.getEmail())) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL_INVALID_REQUEST);
        }
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findAllByAnnouncementIdxAndInterviewNum(announcementIdx, interviewNum)
        .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        Map<Long, List<InterviewEvaluateReadRes>> interviewEvaluateReadAllResMap = new HashMap<>();
        for(InterviewSchedule interviewSchedule : interviewScheduleList){
            List<InterviewParticipate> interviewParticipateList = interviewParticipateRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx())
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));
            for(InterviewParticipate interviewParticipate : interviewParticipateList){
                List<InterviewEvaluate> interviewEvaluateList = interviewEvaluateRepository
                        .findAllByInterviewParticipateIdxAndSeekerIdx(interviewParticipate.getIdx(), interviewParticipate.getSeeker().getIdx())
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_READ_ALL_FAIL));
                for(InterviewEvaluate interviewEvaluate : interviewEvaluateList){
                    InterviewEvaluateResultReadRes interviewEvaluateResultReadRes = InterviewEvaluateResultReadRes.builder()
                            .r1(interviewEvaluate.getInterviewEvaluateResult().getR1())
                            .r2(interviewEvaluate.getInterviewEvaluateResult().getR2())
                            .r3(interviewEvaluate.getInterviewEvaluateResult().getR3())
                            .r4(interviewEvaluate.getInterviewEvaluateResult().getR4())
                            .r5(interviewEvaluate.getInterviewEvaluateResult().getR5())
                            .r6(interviewEvaluate.getInterviewEvaluateResult().getR6())
                            .r7(interviewEvaluate.getInterviewEvaluateResult().getR7())
                            .r8(interviewEvaluate.getInterviewEvaluateResult().getR8())
                            .r9(interviewEvaluate.getInterviewEvaluateResult().getR9())
                            .r10(interviewEvaluate.getInterviewEvaluateResult().getR10())
                            .build();
                    InterviewEvaluateFormReadRes interviewEvaluateFormReadRes = InterviewEvaluateFormReadRes.builder()
                            .q1(interviewEvaluate.getInterviewEvaluateForm().getQ1())
                            .q2(interviewEvaluate.getInterviewEvaluateForm().getQ2())
                            .q3(interviewEvaluate.getInterviewEvaluateForm().getQ3())
                            .q4(interviewEvaluate.getInterviewEvaluateForm().getQ4())
                            .q5(interviewEvaluate.getInterviewEvaluateForm().getQ5())
                            .q6(interviewEvaluate.getInterviewEvaluateForm().getQ6())
                            .q7(interviewEvaluate.getInterviewEvaluateForm().getQ7())
                            .q8(interviewEvaluate.getInterviewEvaluateForm().getQ8())
                            .q9(interviewEvaluate.getInterviewEvaluateForm().getQ9())
                            .q10(interviewEvaluate.getInterviewEvaluateForm().getQ10())
                            .build();
                    InterviewEvaluateReadRes interviewEvaluateReadRes = InterviewEvaluateReadRes.builder()
                            .seekerName(interviewParticipate.getSeeker().getName())
                            .seekerEmail(interviewParticipate.getSeeker().getEmail())
                            .estimatorEmail(interviewParticipate.getEstimator().getEmail())
                            .estimatorName(interviewParticipate.getEstimator().getName())
                            .totalScore(interviewEvaluate.getTotalScore())
                            .comments(interviewEvaluate.getComments())
                            .interviewEvaluateResultReadRes(interviewEvaluateResultReadRes)
                            .interviewEvaluateFormReadRes(interviewEvaluateFormReadRes)
                            .build();
                    if (interviewEvaluateReadAllResMap.containsKey(interviewParticipate.getSeeker().getIdx())) {
                        interviewEvaluateReadAllResMap.get(interviewParticipate.getSeeker().getIdx()).add(interviewEvaluateReadRes);
                    } else {
                        List<InterviewEvaluateReadRes> interviewEvaluateReadResList = new ArrayList<>();
                        interviewEvaluateReadResList.add(interviewEvaluateReadRes);
                        interviewEvaluateReadAllResMap.put(interviewParticipate.getSeeker().getIdx(), interviewEvaluateReadResList);
                    }
                }
            }
        }
        return InterviewEvaluateReadAllRes.builder()
                .interviewEvaluateReadAllResMap(interviewEvaluateReadAllResMap)
                .build();
    }


}
