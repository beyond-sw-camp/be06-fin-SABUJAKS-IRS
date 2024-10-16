package com.service.resume.service;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadAnnouncementRes;
import com.service.common.dto.feign.ReadCustomFormRes;
import com.service.common.dto.feign.ReadSubmissionResumeRes;
import com.service.common.dto.request.resume.*;
import com.service.common.dto.response.resume.*;
import com.service.resume.communication.ResumeFeignClient;
import com.service.resume.entity.*;
import com.service.resume.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
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
    private final CustomResumeRepository customResumeRepository;
    private final SubmissionResumeRepository submissionResumeRepository;
    private final ResumeFeignClient resumeFeignClient;

    @Transactional
    public CreateIntegratedResumeRes createIntegrated(Long memberIdx, String memberRole, CreateResumeReq dto, String fileUrl) throws BaseException {
        if (!Objects.equals(memberRole, "ROLE_SEEKER")) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_MEMBER);
        }
        // 통합지원서 저장 여부 파악 (해당 지원자가 통합 지원서를 작성한 적이 있는지)
        Optional<Resume> resultResume = resumeRepository.findBySeekerIdxAndIntegrated(memberIdx, true);
        if (resultResume.isPresent()) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_INTEGRATED);
        }
        // 지원정보 테이블에 저장
        Resume resume = Resume.builder()
                .seekerIdx(memberIdx)
                .integrated(true) // 통합지원서 - 1개만 가능
                .build();
        resumeRepository.save(resume);
        // 인적사항 테이블에 저장 (반드시)
        PersonalInfo personalInfo = PersonalInfo.builder()
                .resume(resume)
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
        CustomResume.CustomResumeBuilder builder = CustomResume.builder();
        for (String code : dto.getCodes()) {
            if (code.equals("resume_001") && dto.getEducations() != null) {
                builder.resume001(true);
                // 학력 테이블에 저장 (조건 필요)
                for (CreateEducationReq edu : dto.getEducations()) {
                    Education education = Education.builder()
                            .resume(resume)
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
            } else if (code.equals("resume_002") && dto.getPersonalHistories() != null) {
                builder.resume002(true);
                // 경력 테이블에 저장 (조건 필요)
                for (CreatePersonalHistoryReq ph : dto.getPersonalHistories()) {
                    PersonalHistory personalHistory = PersonalHistory.builder()
                            .resume(resume)
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
            } else if (code.equals("resume_003") && dto.getInternsActivities() != null) {
                builder.resume003(true);
                // 인턴·대외활동 테이블에 저장 (조건 필요)
                for (CreateInternsActivityReq ia : dto.getInternsActivities()) {
                    InternsActivity internsActivity = InternsActivity.builder()
                            .resume(resume)
                            .activityDiv(ia.getActivityDiv())
                            .organization(ia.getOrganization())
                            .startAt(ia.getStartAt())
                            .endAt(ia.getEndAt())
                            .contents(ia.getContents())
                            .build();
                    internActivitiesRepository.save(internsActivity);
                }
            } else if (code.equals("resume_004") && dto.getTrainings() != null) {
                builder.resume004(true);
                // 교육이수 테이블에 저장 (조건 필요) resume_004
                for (CreateTrainingReq t : dto.getTrainings()) {
                    Training training = Training.builder()
                            .resume(resume)
                            .trainingName(t.getTrainingName())
                            .organization(t.getOrganization())
                            .startAt(t.getStartAt())
                            .endAt(t.getEndAt())
                            .contents(t.getContents())
                            .build();
                    trainingRepository.save(training);
                }
            } else if (code.equals("resume_005") && dto.getCertifications() != null) {
                builder.resume005(true);
                // 자격증 테이블에 저장 (조건 필요) resume_005
                for (CreateCertificationReq c : dto.getCertifications()) {
                    Certification certification = Certification.builder()
                            .resume(resume)
                            .certName(c.getCertName())
                            .organization(c.getOrganization())
                            .takingAt(c.getTakingAt())
                            .build();
                    certificationRepository.save(certification);
                }
            } else if (code.equals("resume_006") && dto.getAwards() != null) {
                builder.resume006(true);
                // 수상 테이블에 저장 (조건 필요) resume_006
                for (CreateAwardReq a : dto.getAwards()) {
                    Award award = Award.builder()
                            .resume(resume)
                            .awardName(a.getAwardName())
                            .contents(a.getContents())
                            .organization(a.getOrganization())
                            .year(a.getYear())
                            .build();
                    awardRepository.save(award);
                }
            } else if (code.equals("resume_007") && dto.getStudyingAbroads() != null) {
                builder.resume007(true);
                // 해외경험 테이블에 저장 (조건 필요) resume_007
                for (CreateStudyingAbroadReq sa : dto.getStudyingAbroads()) {
                    StudyingAbroad studyingAbroad = StudyingAbroad.builder()
                            .resume(resume)
                            .countryName(sa.getCountryName())
                            .startAt(sa.getStartAt())
                            .endAt(sa.getEndAt())
                            .contents(sa.getContents())
                            .build();
                    studyingAboardRepository.save(studyingAbroad);
                }
            } else if (code.equals("resume_008") && dto.getLanguages() != null) {
                builder.resume008(true);
                // 어학 테이블에 저장 (조건 필요) resume_008
                for (CreateLanguageReq l : dto.getLanguages()) {
                    Language language = Language.builder()
                            .resume(resume)
                            .testDiv(l.getTestDiv())
                            .languageName(l.getLanguageName())
                            .conversationLevel(l.getConversationLevel())
                            .officialTest(l.getOfficialTest())
                            .score(l.getScore())
                            .takingAt(l.getTakingAt())
                            .build();
                    languageRepository.save(language);
                }
            } else if (code.equals("resume_009") && dto.getPortfolios() != null) {
                builder.resume009(true);
                // 포트폴리오 테이블에 저장 (조건 필요) resume_009
                for (CreatePortfolioReq p : dto.getPortfolios()) {
                    Portfolio portfolio = Portfolio.builder()
                            .resume(resume)
                            .portfolioDiv(p.getPortfolioDiv())
                            .portfolioType(p.getPortfolioType())
                            .portfolioUrl(p.getPortfolioUrl())
                            .build();
                    portfolioRepository.save(portfolio);
                }
            } else if (code.equals("resume_010") && dto.getPreferentialEmp() != null) {
                builder.resume010(true);
                // 취업우대&병역에 저장 (조건 필요) resume_010
                PreferentialEmp preferentialEmp = PreferentialEmp.builder()
                        .resume(resume)
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
            } else if (code.equals("resume_011") && dto.getCustomLetters() != null) {
                builder.resume011(true);
                // 자기소개서 테이블에 저장 (조건 필요) resume_011
                for (CreateCustomLetterReq cl : dto.getCustomLetters()) {
                    CustomLetter customLetter = CustomLetter.builder()
                            .resume(resume)
                            .title(cl.getTitle())
                            .charNum(cl.getCharNum())
                            .contents(cl.getContents())
                            .build();
                    customLetterRepository.save(customLetter);
                }
            } else {
                throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL);
            }
        }
        CustomResume customResume = builder.resume(resume).build();
        customResumeRepository.save(customResume);
        return CreateIntegratedResumeRes.builder().resumeIdx(resume.getIdx()).build();
    }

    @Transactional
    public CreateSubmissionResumeRes createSubmission(Long memberIdx, String memberRole, SubmitResumeReq dto, String fileUrl) throws BaseException {
        // TODO OpenFeign으로 공고 idx로 공고엔티티를 받아와야야 되는가? -> 데이터 소유권 조회?
        if (!Objects.equals(memberRole, "ROLE_SEEKER")) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_MEMBER);
        }
        // 지원자가 해당 공고에 이미 지원했는지 확인
        Optional<SubmissionResume> resultResume = submissionResumeRepository.findByAnnouncementIdxAndSeekerIdx(dto.getAnnouncementIdx(), memberIdx);
        if (resultResume.isPresent()) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_RESUME_DUPLICATE);
        }
        // 지원정보 테이블에 저장
        Resume resume = Resume.builder()
                .seekerIdx(memberIdx)
                .integrated(false)
                .build();
        resumeRepository.save(resume);
        // 인적사항 테이블에 저장 (반드시)
        PersonalInfo personalInfo = PersonalInfo.builder()
                .resume(resume)
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
        CustomResume.CustomResumeBuilder builder = CustomResume.builder();
        for (String code : dto.getCodes()) {
            if (code.equals("resume_001") && dto.getEducations() != null) {
                // 학력 테이블에 저장 (조건 필요)
                builder.resume001(true);
                for (CreateEducationReq edu : dto.getEducations()) {
                    Education education = Education.builder()
                            .resume(resume)
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
            } else if (code.equals("resume_002") && dto.getPersonalHistories() != null) {
                builder.resume002(true);
                // 경력 테이블에 저장 (조건 필요)
                for (CreatePersonalHistoryReq ph : dto.getPersonalHistories()) {
                    PersonalHistory personalHistory = PersonalHistory.builder()
                            .resume(resume)
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

            } else if (code.equals("resume_003") && dto.getInternsActivities() != null) {
                builder.resume003(true);
                // 인턴·대외활동 테이블에 저장 (조건 필요)
                for (CreateInternsActivityReq ia : dto.getInternsActivities()) {
                    InternsActivity internsActivity = InternsActivity.builder()
                            .resume(resume)
                            .activityDiv(ia.getActivityDiv())
                            .organization(ia.getOrganization())
                            .startAt(ia.getStartAt())
                            .endAt(ia.getEndAt())
                            .contents(ia.getContents())
                            .build();
                    internActivitiesRepository.save(internsActivity);
                }
            } else if (code.equals("resume_004") && dto.getTrainings() != null) {
                builder.resume004(true);
                // 교육이수 테이블에 저장 (조건 필요) resume_004
                for (CreateTrainingReq t : dto.getTrainings()) {
                    Training training = Training.builder()
                            .resume(resume)
                            .trainingName(t.getTrainingName())
                            .organization(t.getOrganization())
                            .startAt(t.getStartAt())
                            .endAt(t.getEndAt())
                            .contents(t.getContents())
                            .build();
                    trainingRepository.save(training);
                }
            } else if (code.equals("resume_005") && dto.getCertifications() != null) {
                builder.resume005(true);
                // 자격증 테이블에 저장 (조건 필요) resume_005
                for (CreateCertificationReq c : dto.getCertifications()) {
                    Certification certification = Certification.builder()
                            .resume(resume)
                            .certName(c.getCertName())
                            .organization(c.getOrganization())
                            .takingAt(c.getTakingAt())
                            .build();
                    certificationRepository.save(certification);
                }
            } else if (code.equals("resume_006") && dto.getAwards() != null) {
                builder.resume006(true);
                // 수상 테이블에 저장 (조건 필요) resume_006
                for (CreateAwardReq a : dto.getAwards()) {
                    Award award = Award.builder()
                            .resume(resume)
                            .awardName(a.getAwardName())
                            .contents(a.getContents())
                            .organization(a.getOrganization())
                            .year(a.getYear())
                            .build();
                    awardRepository.save(award);
                }
            } else if (code.equals("resume_007") && dto.getStudyingAbroads() != null) {
                builder.resume007(true);
                // 해외경험 테이블에 저장 (조건 필요) resume_007
                for (CreateStudyingAbroadReq sa : dto.getStudyingAbroads()) {
                    StudyingAbroad studyingAbroad = StudyingAbroad.builder()
                            .resume(resume)
                            .countryName(sa.getCountryName())
                            .startAt(sa.getStartAt())
                            .endAt(sa.getEndAt())
                            .contents(sa.getContents())
                            .build();
                    studyingAboardRepository.save(studyingAbroad);
                }
            } else if (code.equals("resume_008") && dto.getLanguages() != null) {
                builder.resume008(true);
                // 어학 테이블에 저장 (조건 필요) resume_008
                for (CreateLanguageReq l : dto.getLanguages()) {
                    Language language = Language.builder()
                            .resume(resume)
                            .testDiv(l.getTestDiv())
                            .languageName(l.getLanguageName())
                            .conversationLevel(l.getConversationLevel())
                            .officialTest(l.getOfficialTest())
                            .score(l.getScore())
                            .takingAt(l.getTakingAt())
                            .build();
                    languageRepository.save(language);
                }
            } else if (code.equals("resume_009") && dto.getPortfolios() != null) {
                builder.resume009(true);
                // 포트폴리오 테이블에 저장 (조건 필요) resume_009
                for (CreatePortfolioReq p : dto.getPortfolios()) {
                    Portfolio portfolio = Portfolio.builder()
                            .resume(resume)
                            .portfolioDiv(p.getPortfolioDiv())
                            .portfolioType(p.getPortfolioType())
                            .portfolioUrl(p.getPortfolioUrl())
                            .build();
                    portfolioRepository.save(portfolio);
                }
            } else if (code.equals("resume_010") && dto.getPreferentialEmp() != null) {
                builder.resume010(true);
                // 취업우대&병역에 저장 (조건 필요) resume_010
                PreferentialEmp preferentialEmp = PreferentialEmp.builder()
                        .resume(resume)
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
            } else if (code.equals("resume_011") && dto.getCustomLetters() != null) {
                builder.resume011(true);
                // 자기소개서 테이블에 저장 (조건 필요) resume_011
                for (CreateCustomLetterReq cl : dto.getCustomLetters()) {
                    CustomLetter customLetter = CustomLetter.builder()
                            .resume(resume)
                            .title(cl.getTitle())
                            .charNum(cl.getCharNum())
                            .contents(cl.getContents())
                            .build();
                    customLetterRepository.save(customLetter);
                }
            } else {
                throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL);
            }
        }
        CustomResume customResume = builder.resume(resume).build();
        customResumeRepository.save(customResume);
        // 공고지원서 테이블에 저장
        SubmissionResume submissionResume = SubmissionResume.builder()
                .resumeTitle(dto.getResumeTitle())
                .resume(resume)
                .announcementIdx(dto.getAnnouncementIdx())
                .seekerIdx(memberIdx)
                .build();
        submissionResumeRepository.save(submissionResume);
        return CreateSubmissionResumeRes.builder().submissionResumeIdx(resume.getIdx()).build();
    }

    @Transactional
    public EnterResumeRes enterSubmission(Long memberIdx, String memberEmail, String memberRole, Long announcementIdx) throws BaseException {
        if (!Objects.equals(memberRole, "ROLE_SEEKER")) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_MEMBER);
        }
        // 지원자가 해당 공고 이미 지원했을때
        Optional<SubmissionResume> resultSubmissionResume = submissionResumeRepository.findByAnnouncementIdxAndSeekerIdx(announcementIdx, memberIdx);
        if (resultSubmissionResume.isPresent()) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_RESUME_DUPLICATE);
        }
        EnterResumeRes.EnterResumeResBuilder responseBuilder = EnterResumeRes.builder();
        // 지원서 맞춤 양식 테이블 조회
        ReadCustomFormRes readCustomFormRes = resumeFeignClient.readAllCustomFormRes(announcementIdx);
        List<String> formCodes = getCodes2(readCustomFormRes.getCustomResumeFormList());
        responseBuilder.codes(formCodes);
        if (formCodes.contains("resume_011")) { // 자기소개서
            List<String> customLetterList = readCustomFormRes.getCustomLetterList();
            if (!customLetterList.isEmpty()) {
                // 자기소개서 맞춤 양식 테이블 조회 (공고 idx로)
                List<ReadCustomLetterFormRes> customLetterFormResList = new ArrayList<>();
                for (String customLetterString : customLetterList) {
                    String[] parts = customLetterString.split(" / ");
                    if (parts.length == 2) {
                        String title = parts[0];
                        int chatLimit = Integer.parseInt(parts[1]);
                        ReadCustomLetterFormRes customLetterFormRes = ReadCustomLetterFormRes.builder()
                                .title(title)
                                .chatLimit(chatLimit)
                                .build();
                        customLetterFormResList.add(customLetterFormRes);
                    }
                }
                responseBuilder.customLetterForms(customLetterFormResList);
            }
        }
        // 지원정보 테이블 조회 (통합 지원서)
        Optional<Resume> resultResume = resumeRepository.findBySeekerIdxAndIntegrated(memberIdx, true);
        if (resultResume.isPresent()) {
            // 인적사항
            Resume resume = resultResume.get();
            Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeIdx(resume.getIdx());
            if (resultPersonalInfo.isPresent()) {
                PersonalInfo personalInfo = resultPersonalInfo.get();
                ReadPersonalInfoRes personalInfoRes = ReadPersonalInfoRes.builder()
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
            if (formCodes.contains("resume_001")) { // 학력
                List<Education> resultEducations = educationRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultEducations.isEmpty()) {
                    List<ReadEducationRes> educationResList = new ArrayList<>();
                    for (Education education : resultEducations) {
                        ReadEducationRes educationRes = ReadEducationRes.builder()
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
                List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultPersonalHistories.isEmpty()) {
                    List<ReadPersonalHistoryRes> personalHistoryResList = new ArrayList<>();
                    for (PersonalHistory personalHistory : resultPersonalHistories) {
                        ReadPersonalHistoryRes personalHistoryRes = ReadPersonalHistoryRes.builder()
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
                List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultInternsActivities.isEmpty()) {
                    List<ReadInternsActivityRes> internsActivityResList = new ArrayList<>();
                    for (InternsActivity internsActivity : resultInternsActivities) {
                        ReadInternsActivityRes internsActivityRes = ReadInternsActivityRes.builder()
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
                List<Training> resultTrainings = trainingRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultTrainings.isEmpty()) {
                    List<ReadTrainingRes> trainingResList = new ArrayList<>();
                    for (Training training : resultTrainings) {
                        ReadTrainingRes trainingRes = ReadTrainingRes.builder()
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
                List<Certification> resultCertifications = certificationRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultCertifications.isEmpty()) {
                    List<ReadCertificationRes> certificationResList = new ArrayList<>();
                    for (Certification certification : resultCertifications) {
                        ReadCertificationRes certificationRes = ReadCertificationRes.builder()
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
                List<Award> resultAwards = awardRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultAwards.isEmpty()) {
                    List<ReadAwardRes> awardResList = new ArrayList<>();
                    for (Award award : resultAwards) {
                        ReadAwardRes awardRes = ReadAwardRes.builder()
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
                List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultStudyingAbroads.isEmpty()) {
                    List<ReadStudyingAbroadRes> studyingAbroadResList = new ArrayList<>();
                    for (StudyingAbroad studyingAbroad : resultStudyingAbroads) {
                        ReadStudyingAbroadRes studyingAbroadRes = ReadStudyingAbroadRes.builder()
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
                List<Language> resultLanguages = languageRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultLanguages.isEmpty()) {
                    List<ReadLanguageRes> languageResList = new ArrayList<>();
                    for (Language language : resultLanguages) {
                        ReadLanguageRes languageRes = ReadLanguageRes.builder()
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
//                        List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeIdx(resultResumeInfo.get().getIdx());
//                        if(!resultPortfolios.isEmpty()) {
//                            List<ReadPortfolioRes> portfolioResList = new ArrayList<>();
//                            for(Portfolio portfolio : resultPortfolios) {
//                                ReadPortfolioRes portfolioRes = ReadPortfolioRes.builder()
//                                        .portfolioDiv(portfolio.getPortfolioDiv())
//                                        .portfolioType(portfolio.getPortfolioType())
//                                        .portfolioUrl(portfolio.getPortfolioUrl())
//                                        .build();
//                                portfolioResList.add(portfolioRes);
//                            }
//                            responseBuilder.portfolios(portfolioResList);
//                        }
//                    }
            if (formCodes.contains("resume_010")) { // 취업우대&병역
                Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeIdx(resume.getIdx());
                if (resultPreferentialEmp.isPresent()) {
                    PreferentialEmp preferentialEmp = resultPreferentialEmp.get();
                    ReadPreferentialEmpRes preferentialEmpRes = ReadPreferentialEmpRes.builder()
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
            return responseBuilder.integrated(true).build();
        }
        return responseBuilder.integrated(false).build();
    }

    @Transactional
    public ReadIntegratedResumeRes readIntegrated(Long memberIdx, String memberRole) throws BaseException {
        if (!Objects.equals(memberRole, "ROLE_SEEKER")) {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_MEMBER);
        }
        // 지원정보 테이블 조회 (integration == 1)
        Resume resume = resumeRepository.findBySeekerIdxAndIntegrated(memberIdx, true)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_INTEGRATED_RESUME));
        ReadIntegratedResumeRes.ReadIntegratedResumeResBuilder responseBuilder = ReadIntegratedResumeRes.builder();
        // 인적사항 조회
        Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeIdx(resume.getIdx());
        if (resultPersonalInfo.isPresent()) {
            PersonalInfo personalInfo = resultPersonalInfo.get();
            ReadPersonalInfoRes personalInfoRes = ReadPersonalInfoRes.builder()
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
        List<CustomResume> resultCustomResumes = customResumeRepository.findAllByResumeIdx(resume.getIdx());
        if (!resultCustomResumes.isEmpty()) {
            List<String> formCodes = getCodes(resultCustomResumes);
            // 나머지 조회
            if (formCodes.contains("resume_001")) { // 학력
                List<Education> resultEducations = educationRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultEducations.isEmpty()) {
                    ArrayList<ReadEducationRes> educationResList = new ArrayList<>();
                    for (Education education : resultEducations) {
                        ReadEducationRes educationRes = ReadEducationRes.builder()
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
                List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultPersonalHistories.isEmpty()) {
                    ArrayList<ReadPersonalHistoryRes> personalHistoryResList = new ArrayList<>();
                    for (PersonalHistory personalHistory : resultPersonalHistories) {
                        ReadPersonalHistoryRes personalHistoryRes = ReadPersonalHistoryRes.builder()
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
                List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultInternsActivities.isEmpty()) {
                    ArrayList<ReadInternsActivityRes> internsActivityResList = new ArrayList<>();
                    for (InternsActivity internsActivity : resultInternsActivities) {
                        ReadInternsActivityRes internsActivityRes = ReadInternsActivityRes.builder()
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
                List<Training> resultTrainings = trainingRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultTrainings.isEmpty()) {
                    ArrayList<ReadTrainingRes> trainingResList = new ArrayList<>();
                    for (Training training : resultTrainings) {
                        ReadTrainingRes trainingRes = ReadTrainingRes.builder()
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
                List<Certification> resultCertifications = certificationRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultCertifications.isEmpty()) {
                    ArrayList<ReadCertificationRes> certificationResList = new ArrayList<>();
                    for (Certification certification : resultCertifications) {
                        ReadCertificationRes certificationRes = ReadCertificationRes.builder()
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
                List<Award> resultAwards = awardRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultAwards.isEmpty()) {
                    ArrayList<ReadAwardRes> awardResList = new ArrayList<>();
                    for (Award award : resultAwards) {
                        ReadAwardRes awardRes = ReadAwardRes.builder()
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
                List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultStudyingAbroads.isEmpty()) {
                    ArrayList<ReadStudyingAbroadRes> studyingAbroadResList = new ArrayList<>();
                    for (StudyingAbroad studyingAbroad : resultStudyingAbroads) {
                        ReadStudyingAbroadRes studyingAbroadRes = ReadStudyingAbroadRes.builder()
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
                List<Language> resultLanguages = languageRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultLanguages.isEmpty()) {
                    ArrayList<ReadLanguageRes> languageResList = new ArrayList<>();
                    for (Language language : resultLanguages) {
                        ReadLanguageRes languageRes = ReadLanguageRes.builder()
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
                List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultPortfolios.isEmpty()) {
                    List<ReadPortfolioRes> portfolioResList = new ArrayList<>();
                    for (Portfolio portfolio : resultPortfolios) {
                        ReadPortfolioRes portfolioRes = ReadPortfolioRes.builder()
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
                Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeIdx(resume.getIdx());
                if (resultPreferentialEmp.isPresent()) {
                    PreferentialEmp preferentialEmp = resultPreferentialEmp.get();
                    ReadPreferentialEmpRes preferentialEmpRes = ReadPreferentialEmpRes.builder()
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
                List<CustomLetter> resultCustomLetter = customLetterRepository.findAllByResumeIdx(resume.getIdx());
                if (!resultCustomLetter.isEmpty()) {
                    // 자기소개서 맞춤 양식 테이블 조회 (공고 idx로)
                    List<ReadCustomLetterRes> customLetterResList = new ArrayList<>();
                    for (CustomLetter customLetter : resultCustomLetter) {
                        ReadCustomLetterRes customLetterRes = ReadCustomLetterRes.builder()
                                .title(customLetter.getTitle())
                                .charNum(customLetter.getCharNum())
                                .contents(customLetter.getContents())
                                .build();
                        customLetterResList.add(customLetterRes);
                    }
                    responseBuilder.customLetters(customLetterResList);
                }
            }
            return responseBuilder.codes(formCodes).build();
        }
        return responseBuilder.build();
    }

    @Transactional
    public ReadSubmissionResumeRes readSubmission(Long memberIdx, String memberEmail, String memberRole, Long submissionResumeIdx, Long seekerIdx, Long announcementIdx) throws BaseException {
        SubmissionResume submissionResume;
        if(Objects.equals(memberRole, "ROLE_SEEKER") || Objects.equals(memberRole, "ROLE_RECRUITER")){
             submissionResume = submissionResumeRepository.findBySubmissionResumeIdx(submissionResumeIdx)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_SUBMISSION_RESUME));
            if (!((Objects.equals(submissionResume.getSeekerIdx(), memberIdx)) ||
                    (Objects.equals(resumeFeignClient.readAnnouncement(memberIdx).getRecruiterEmail(), memberEmail)))){
                throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_INVALID_REQUEST);
            }
        }else {
            submissionResume = submissionResumeRepository.findByAnnouncementIdxAndSeekerIdx(announcementIdx, seekerIdx)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_SUBMISSION_RESUME));
        }
        ReadSubmissionResumeRes.ReadSubmissionResumeResBuilder responseBuilder = ReadSubmissionResumeRes.builder();
        responseBuilder.resumeTitle(submissionResume.getResumeTitle());
        responseBuilder.resumedAt(submissionResume.getResumedAt());
        responseBuilder.seekerIdx(submissionResume.getSeekerIdx());
        responseBuilder.announcementIdx(submissionResume.getAnnouncementIdx());
        // TODO: total_process 테이블에서 조회하기
//        Optional<TotalProcess> resultTotalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(submissionResume.getAnnouncementIdx(), submissionResume.getSeekerIdx());
//        if (resultTotalProcess.isPresent()) {
//            responseBuilder.docPassed(resultTotalProcess.get().getResumeResult());
//        } else {
//            responseBuilder.docPassed(null);
//        }
        // 지원정보 테이블 조회
        Resume resultResume = resumeRepository.findByResumeIdx(submissionResume.getResume().getIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_SUBMISSION_RESUME));
        // 인적사항 조회
        Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeIdx(resultResume.getIdx());
        if (resultPersonalInfo.isPresent()) {
            PersonalInfo personalInfo = resultPersonalInfo.get();
            ReadPersonalInfoRes personalInfoRes = ReadPersonalInfoRes.builder()
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
        List<CustomResume> resultCustomResumes = customResumeRepository.findAllByResumeIdx(resultResume.getIdx());
        if (!resultCustomResumes.isEmpty()) {
            List<String> formCodes = getCodes(resultCustomResumes);
            // 나머지 조회
            if (formCodes.contains("resume_001")) { // 학력
                List<Education> resultEducations = educationRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultEducations.isEmpty()) {
                    ArrayList<ReadEducationRes> educationResList = new ArrayList<>();
                    for (Education education : resultEducations) {
                        ReadEducationRes educationRes = ReadEducationRes.builder()
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
                List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultPersonalHistories.isEmpty()) {
                    ArrayList<ReadPersonalHistoryRes> personalHistoryResList = new ArrayList<>();
                    for (PersonalHistory personalHistory : resultPersonalHistories) {
                        ReadPersonalHistoryRes personalHistoryRes = ReadPersonalHistoryRes.builder()
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
                List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultInternsActivities.isEmpty()) {
                    ArrayList<ReadInternsActivityRes> internsActivityResList = new ArrayList<>();
                    for (InternsActivity internsActivity : resultInternsActivities) {
                        ReadInternsActivityRes internsActivityRes = ReadInternsActivityRes.builder()
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
                List<Training> resultTrainings = trainingRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultTrainings.isEmpty()) {
                    ArrayList<ReadTrainingRes> trainingResList = new ArrayList<>();
                    for (Training training : resultTrainings) {
                        ReadTrainingRes trainingRes = ReadTrainingRes.builder()
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
                List<Certification> resultCertifications = certificationRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultCertifications.isEmpty()) {
                    ArrayList<ReadCertificationRes> certificationResList = new ArrayList<>();
                    for (Certification certification : resultCertifications) {
                        ReadCertificationRes certificationRes = ReadCertificationRes.builder()
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
                List<Award> resultAwards = awardRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultAwards.isEmpty()) {
                    ArrayList<ReadAwardRes> awardResList = new ArrayList<>();
                    for (Award award : resultAwards) {
                        ReadAwardRes awardRes = ReadAwardRes.builder()
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
                List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultStudyingAbroads.isEmpty()) {
                    ArrayList<ReadStudyingAbroadRes> studyingAbroadResList = new ArrayList<>();
                    for (StudyingAbroad studyingAbroad : resultStudyingAbroads) {
                        ReadStudyingAbroadRes studyingAbroadRes = ReadStudyingAbroadRes.builder()
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
                List<Language> resultLanguages = languageRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultLanguages.isEmpty()) {
                    ArrayList<ReadLanguageRes> languageResList = new ArrayList<>();
                    for (Language language : resultLanguages) {
                        ReadLanguageRes languageRes = ReadLanguageRes.builder()
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
                List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultPortfolios.isEmpty()) {
                    List<ReadPortfolioRes> portfolioResList = new ArrayList<>();
                    for (Portfolio portfolio : resultPortfolios) {
                        ReadPortfolioRes portfolioRes = ReadPortfolioRes.builder()
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
                Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeIdx(resultResume.getIdx());
                if (resultPreferentialEmp.isPresent()) {
                    PreferentialEmp preferentialEmp = resultPreferentialEmp.get();
                    ReadPreferentialEmpRes preferentialEmpRes = ReadPreferentialEmpRes.builder()
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
                List<CustomLetter> resultCustomLetter = customLetterRepository.findAllByResumeIdx(resultResume.getIdx());
                if (!resultCustomLetter.isEmpty()) {
                    // 자기소개서 맞춤 양식 테이블 조회 (공고 idx로)
                    List<ReadCustomLetterRes> customLetterResList = new ArrayList<>();
                    for (CustomLetter customLetter : resultCustomLetter) {
                        ReadCustomLetterRes customLetterRes = ReadCustomLetterRes.builder()
                                .title(customLetter.getTitle())
                                .charNum(customLetter.getCharNum())
                                .contents(customLetter.getContents())
                                .build();
                        customLetterResList.add(customLetterRes);
                    }
                    responseBuilder.customLetters(customLetterResList);
                }
            }
            return responseBuilder.codes(formCodes).build();
        }
        return responseBuilder.build();
    }

    @Transactional
    public Page<ReadAllSeResumeRes> readAllSeSubmission(Long memberIdx, String memberEmail, String memberRole, Integer page, Integer size) throws BaseException {
        // 지원자 idx로 공고지원서 테이블 조회 (페이징 처리)
        Pageable pageable = PageRequest.of(page, size);
        Page<SubmissionResume> resultSubmissionResume = submissionResumeRepository.findAllBySeekerIdx(memberIdx, pageable)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_SUBMISSION_RESUME));
        List<ReadAllSeResumeRes> resumeReadAllResList = new ArrayList<>();
        for (SubmissionResume submissionResume : resultSubmissionResume) {
            ReadAnnouncementRes readAnnouncementRes = resumeFeignClient.readAnnouncement(submissionResume.getAnnouncementIdx());
            // TODO: TOTAL PROCESS ;;;
            // total_process 테이블에서 조회하기
//                        Optional<TotalProcess> resultTotalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(resume.getAnnouncement().getIdx(), resume.getSeeker().getIdx());
//                        if (resultTotalProcess.isPresent()) {
//                            TotalProcess totalProcess = resultTotalProcess.get();
            ReadAllSeResumeRes resumeReadAllRes = ReadAllSeResumeRes.builder()
                    .resumeIdx(submissionResume.getIdx())
                    .resumeTitle(submissionResume.getResumeTitle())
                    .resumedAt(submissionResume.getResumedAt())
                    .announcementIdx(readAnnouncementRes.getAnnouncementIdx())
                    .announcementTitle(readAnnouncementRes.getAnnouncementTitle())
                    .announcementStart(readAnnouncementRes.getAnnouncementStart())
                    .announcementEnd(readAnnouncementRes.getAnnouncementEnd())
                    .companyName(readAnnouncementRes.getCompanyName())
//                                    .resumeResult(totalProcess.getResumeResult())
//                                    .interviewOneResult(totalProcess.getInterviewOneResult())
//                                    .interviewTwoResult(totalProcess.getInterviewTwoResult())
//                                    .finalResult(totalProcess.getFinalResult())
                    .build();
            resumeReadAllResList.add(resumeReadAllRes);
//                        } else {
//                            ResumeReadAllRes resumeReadAllRes = ResumeReadAllRes.builder()
//                                    .resumeIdx(resume.getIdx())
//                                    .resumeTitle(resume.getResumeTitle())
//                                    .resumedAt(resume.getResumedAt())
//                                    .announcementIdx(resultAnnouncement.get().getIdx())
//                                    .announcementTitle(resultAnnouncement.get().getTitle())
//                                    .announcementStart(resultAnnouncement.get().getAnnouncementStart())
//                                    .announcementEnd(resultAnnouncement.get().getAnnouncementEnd())
//                                    .companyName(resultCompany.get().getName())
//                                    .resumeResult(null)
//                                    .interviewOneResult(null)
//                                    .interviewTwoResult(null)
//                                    .finalResult(null)
//                                    .build();
//                            resumeReadAllResList.add(resumeReadAllRes);
//                        }
        }
        return new PageImpl<>(resumeReadAllResList, pageable, resultSubmissionResume.getTotalElements());
    }

    public Page<ReadAllReResumeRes> readAllReSubmission(Long memberIdx, String memberEmail, String memberRole, Long announcementIdx, Integer page, Integer size) throws BaseException {
        ReadAnnouncementRes readAnnouncementRes = resumeFeignClient.readAnnouncement(announcementIdx);
        if (!Objects.equals(readAnnouncementRes.getRecruiterEmail(), memberEmail)) {
            throw new BaseException(BaseResponseMessage.RESUME_READ_FAIL_INVALID_REQUEST);
        }
        // 공고지원서 테이블 조회 (페이징 처리)
        Pageable pageable = PageRequest.of(page, size);
        Page<SubmissionResume> resultResumes = submissionResumeRepository.findAllByAnnouncementIdx(announcementIdx, pageable)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_SUBMISSION_RESUME));
        List<ReadAllReResumeRes> readAllReResumeResList = new ArrayList<>();
        for (SubmissionResume submissionResume : resultResumes) {
            // TODO: total_process 테이블에서 조회하기
//                Optional<TotalProcess> resultTotalProcess = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(
//                        resume.getAnnouncement().getIdx(), resume.getSeeker().getIdx()
//                );
//                if (resultTotalProcess.isPresent()) {
//                    TotalProcess totalProcess = resultTotalProcess.get();
            ReadAllReResumeRes readAllReResumeRes = ReadAllReResumeRes.builder()
                    .resumeIdx(submissionResume.getIdx())
                    .resumeTitle(submissionResume.getResumeTitle())
                    .resumedAt(submissionResume.getResumedAt())
//                            .resumeResult(totalProcess.getResumeResult())
//                            .interviewOneResult(totalProcess.getInterviewOneResult())
//                            .interviewTwoResult(totalProcess.getInterviewTwoResult())
//                            .finalResult(totalProcess.getFinalResult())
//                            TODO: 지원자 정보 조회
//                            .seekerName(submissionResume.re().getName())
                    .seekerIdx(submissionResume.getResume().getSeekerIdx())
                    .build();
            readAllReResumeResList.add(readAllReResumeRes);
//                } else {
//                    ReadAllReResumeRes resumeReadAllRecruiterRes = ReadAllReResumeRes.builder()
//                            .resumeIdx(resume.getIdx())
//                            .resumeTitle(resume.getResumeTitle())
//                            .resumedAt(resume.getResumedAt())
//                            .resumeResult(null)
//                            .interviewOneResult(null)
//                            .interviewTwoResult(null)
//                            .finalResult(null)
//                            .seekerName(resume.getSeeker().getName())
//                            .build();
//                    resumeReadAllRecruiterResList.add(resumeReadAllRecruiterRes);
        }
        return new PageImpl<>(readAllReResumeResList, pageable, resultResumes.getTotalElements());
    }

    public List<String> getCodes (List < CustomResume > customResumeList) {
            List<String> formCodes = new ArrayList<>();
            for (CustomResume customResume : customResumeList) {
                if (customResume.getResume001() != null && customResume.getResume001()) {
                    formCodes.add("resume_001");
                } // 인적 사항
                if (customResume.getResume002() != null && customResume.getResume002()) {
                    formCodes.add("resume_002");
                } // 경력
                if (customResume.getResume003() != null && customResume.getResume003()) {
                    formCodes.add("resume_003");
                } // 인턴·대외활동
                if (customResume.getResume004() != null && customResume.getResume004()) {
                    formCodes.add("resume_004");
                } // 교육이수
                if (customResume.getResume005() != null && customResume.getResume005()) {
                    formCodes.add("resume_005");
                } // 자격증
                if (customResume.getResume006() != null && customResume.getResume006()) {
                    formCodes.add("resume_006");
                } // 수상
                if (customResume.getResume007() != null && customResume.getResume007()) {
                    formCodes.add("resume_007");
                } // 해외경험
                if (customResume.getResume008() != null && customResume.getResume008()) {
                    formCodes.add("resume_008");
                } // 어학
                if (customResume.getResume009() != null && customResume.getResume009()) {
                    formCodes.add("resume_009");
                } // 포트폴리오
                if (customResume.getResume010() != null && customResume.getResume010()) {
                    formCodes.add("resume_010");
                } // 취업우대&병역
                if (customResume.getResume011() != null && customResume.getResume011()) {
                    formCodes.add("resume_011");
                } // 자기소개서
            }
            return formCodes;
}

    public List<String> getCodes2 (List <String> customResumeFormList) {
        List<String> formCodes = new ArrayList<>();
        for (String customResume : customResumeFormList) {
            if (Objects.equals(customResume, "학력")) {
                formCodes.add("resume_001");
            } // 인적 사항
            if (Objects.equals(customResume, "경력")) {
                formCodes.add("resume_002");
            } // 경력
            if (Objects.equals(customResume, "인턴·대외활동")) {
                formCodes.add("resume_003");
            } // 인턴·대외활동
            if (Objects.equals(customResume, "교육이수")) {
                formCodes.add("resume_004");
            } // 교육이수
            if (Objects.equals(customResume, "자격증")) {
                formCodes.add("resume_005");
            } // 자격증
            if (Objects.equals(customResume, "수상")) {
                formCodes.add("resume_006");
            } // 수상
            if (Objects.equals(customResume, "해외경험")) {
                formCodes.add("resume_007");
            } // 해외경험
            if (Objects.equals(customResume, "어학")) {
                formCodes.add("resume_008");
            } // 어학
            if (Objects.equals(customResume, "포트폴리오")) {
                formCodes.add("resume_009");
            } // 포트폴리오
            if (Objects.equals(customResume, "취업우대&병역")) {
                formCodes.add("resume_010");
            } // 취업우대&병역
            if (Objects.equals(customResume, "자기소개서")) {
                formCodes.add("resume_011");
            } // 자기소개서
        }
        return formCodes;
    }
}

//    @Transactional
//    public ReadSubmissionResumeRes readEsSubmission(Long memberIdx, String memberEmail, String memberRole, Long seekerIdx, Long announcementIdx) throws BaseException {
//        SubmissionResume submissionResume = submissionResumeRepository.findByAnnouncementIdxAndSeekerIdx(announcementIdx, seekerIdx)
//                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_SUBMISSION_RESUME));
//        ReadSubmissionResumeRes.ReadSubmissionResumeResBuilder responseBuilder = ReadSubmissionResumeRes.builder();
//        responseBuilder.resumeTitle(submissionResume.getResumeTitle());
//        responseBuilder.resumedAt(submissionResume.getResumedAt());
//        responseBuilder.seekerIdx(submissionResume.getSeekerIdx());
//        responseBuilder.announcementIdx(submissionResume.getAnnouncementIdx());
//        // 지원정보 테이블 조회
//        Resume resultResume = resumeRepository.findByResumeIdx(submissionResume.getResume().getIdx())
//                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESUME_READ_FAIL_NOT_REGISTER_SUBMISSION_RESUME));
//        // 인적사항 조회
//        Optional<PersonalInfo> resultPersonalInfo = personalInfoRepository.findByResumeIdx(resultResume.getIdx());
//        if (resultPersonalInfo.isPresent()) {
//            PersonalInfo personalInfo = resultPersonalInfo.get();
//            ReadPersonalInfoRes personalInfoRes = ReadPersonalInfoRes.builder()
//                    .name(personalInfo.getName())
//                    .birth(personalInfo.getBirth())
//                    .gender(personalInfo.getGender())
//                    .email(personalInfo.getEmail())
//                    .address(personalInfo.getAddress())
//                    .phone(personalInfo.getPhone())
//                    .tel(personalInfo.getTel())
//                    .profileImg(personalInfo.getProfileImg())
//                    .build();
//            responseBuilder.personalInfo(personalInfoRes);
//        }
//        // 맞춤 지원정보 테이블 조회
//        List<CustomResume> resultCustomResumes = customResumeRepository.findAllByResumeIdx(resultResume.getIdx());
//        if (!resultCustomResumes.isEmpty()) {
//            List<String> formCodes = getCodes(resultCustomResumes);
//            // 나머지 조회
//            if (formCodes.contains("resume_001")) { // 학력
//                List<Education> resultEducations = educationRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultEducations.isEmpty()) {
//                    ArrayList<ReadEducationRes> educationResList = new ArrayList<>();
//                    for (Education education : resultEducations) {
//                        ReadEducationRes educationRes = ReadEducationRes.builder()
//                                .schoolDiv(education.getSchoolDiv())
//                                .schoolName(education.getSchoolName())
//                                .enteredAt(education.getEnteredAt())
//                                .graduatedAt(education.getGraduatedAt())
//                                .graduationStatus(education.getGraduationStatus())
//                                .majorName(education.getMajorName())
//                                .grade(education.getGrade())
//                                .totalGrade(education.getTotalGrade())
//                                .transfer(education.getTransfer())
//                                .majorType(education.getMajorType())
//                                .otherMajor(education.getOtherMajor())
//                                .graduationWork(education.getGraduationWork())
//                                .degree(education.getDegree())
//                                .qualificationExam(education.getQualificationExam())
//                                .passedAt(education.getPassedAt())
//                                .build();
//                        educationResList.add(educationRes);
//                    }
//                    responseBuilder.educations(educationResList);
//                }
//            }
//            if (formCodes.contains("resume_002")) { // 경력
//                List<PersonalHistory> resultPersonalHistories = personalHistoryRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultPersonalHistories.isEmpty()) {
//                    ArrayList<ReadPersonalHistoryRes> personalHistoryResList = new ArrayList<>();
//                    for (PersonalHistory personalHistory : resultPersonalHistories) {
//                        ReadPersonalHistoryRes personalHistoryRes = ReadPersonalHistoryRes.builder()
//                                .companyName(personalHistory.getCompanyName())
//                                .deptName(personalHistory.getDeptName())
//                                .enteredAt(personalHistory.getEnteredAt())
//                                .quitAt(personalHistory.getQuitAt())
//                                .empStatus(personalHistory.getEmpStatus())
//                                .position(personalHistory.getPosition())
//                                .job(personalHistory.getJob())
//                                .salary(personalHistory.getSalary())
//                                .work(personalHistory.getWork())
//                                .build();
//                        personalHistoryResList.add(personalHistoryRes);
//                    }
//                    responseBuilder.personalHistories(personalHistoryResList);
//                }
//            }
//            if (formCodes.contains("resume_003")) { // 인턴&대외활동
//                List<InternsActivity> resultInternsActivities = internActivitiesRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultInternsActivities.isEmpty()) {
//                    ArrayList<ReadInternsActivityRes> internsActivityResList = new ArrayList<>();
//                    for (InternsActivity internsActivity : resultInternsActivities) {
//                        ReadInternsActivityRes internsActivityRes = ReadInternsActivityRes.builder()
//                                .activityDiv(internsActivity.getActivityDiv())
//                                .organization(internsActivity.getOrganization())
//                                .startAt(internsActivity.getStartAt())
//                                .endAt(internsActivity.getEndAt())
//                                .contents(internsActivity.getContents())
//                                .build();
//                        internsActivityResList.add(internsActivityRes);
//                    }
//                    responseBuilder.internsActivities(internsActivityResList);
//                }
//            }
//            if (formCodes.contains("resume_004")) { // 교육이수
//                List<Training> resultTrainings = trainingRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultTrainings.isEmpty()) {
//                    ArrayList<ReadTrainingRes> trainingResList = new ArrayList<>();
//                    for (Training training : resultTrainings) {
//                        ReadTrainingRes trainingRes = ReadTrainingRes.builder()
//                                .trainingName(training.getTrainingName())
//                                .organization(training.getOrganization())
//                                .startAt(training.getStartAt())
//                                .endAt(training.getEndAt())
//                                .contents(training.getContents())
//                                .build();
//                        trainingResList.add(trainingRes);
//                    }
//                    responseBuilder.trainings(trainingResList);
//                }
//            }
//            if (formCodes.contains("resume_005")) { // 자격증
//                List<Certification> resultCertifications = certificationRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultCertifications.isEmpty()) {
//                    ArrayList<ReadCertificationRes> certificationResList = new ArrayList<>();
//                    for (Certification certification : resultCertifications) {
//                        ReadCertificationRes certificationRes = ReadCertificationRes.builder()
//                                .certName(certification.getCertName())
//                                .organization(certification.getOrganization())
//                                .takingAt(certification.getTakingAt())
//                                .build();
//                        certificationResList.add(certificationRes);
//                    }
//                    responseBuilder.certifications(certificationResList);
//                }
//            }
//            if (formCodes.contains("resume_006")) { // 수상
//                List<Award> resultAwards = awardRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultAwards.isEmpty()) {
//                    ArrayList<ReadAwardRes> awardResList = new ArrayList<>();
//                    for (Award award : resultAwards) {
//                        ReadAwardRes awardRes = ReadAwardRes.builder()
//                                .awardName(award.getAwardName())
//                                .contents(award.getContents())
//                                .organization(award.getOrganization())
//                                .year(award.getYear())
//                                .build();
//                        awardResList.add(awardRes);
//                    }
//                    responseBuilder.awards(awardResList);
//                }
//            }
//            if (formCodes.contains("resume_007")) { // 해외경험
//                List<StudyingAbroad> resultStudyingAbroads = studyingAboardRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultStudyingAbroads.isEmpty()) {
//                    ArrayList<ReadStudyingAbroadRes> studyingAbroadResList = new ArrayList<>();
//                    for (StudyingAbroad studyingAbroad : resultStudyingAbroads) {
//                        ReadStudyingAbroadRes studyingAbroadRes = ReadStudyingAbroadRes.builder()
//                                .countryName(studyingAbroad.getCountryName())
//                                .startAt(studyingAbroad.getStartAt())
//                                .endAt(studyingAbroad.getEndAt())
//                                .contents(studyingAbroad.getContents())
//                                .build();
//                        studyingAbroadResList.add(studyingAbroadRes);
//                    }
//                    responseBuilder.studyingAbroads(studyingAbroadResList);
//                }
//
//            }
//            if (formCodes.contains("resume_008")) { // 어학
//                List<Language> resultLanguages = languageRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultLanguages.isEmpty()) {
//                    ArrayList<ReadLanguageRes> languageResList = new ArrayList<>();
//                    for (Language language : resultLanguages) {
//                        ReadLanguageRes languageRes = ReadLanguageRes.builder()
//                                .testDiv(language.getTestDiv())
//                                .languageName(language.getLanguageName())
//                                .conversationLevel(language.getConversationLevel())
//                                .officialTest(language.getOfficialTest())
//                                .score(language.getScore())
//                                .takingAt(language.getTakingAt())
//                                .build();
//                        languageResList.add(languageRes);
//                    }
//                    responseBuilder.languages(languageResList);
//                }
//            }
//            if (formCodes.contains("resume_009")) { // 포트폴리오
//                List<Portfolio> resultPortfolios = portfolioRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultPortfolios.isEmpty()) {
//                    List<ReadPortfolioRes> portfolioResList = new ArrayList<>();
//                    for (Portfolio portfolio : resultPortfolios) {
//                        ReadPortfolioRes portfolioRes = ReadPortfolioRes.builder()
//                                .portfolioDiv(portfolio.getPortfolioDiv())
//                                .portfolioType(portfolio.getPortfolioType())
//                                .portfolioUrl(portfolio.getPortfolioUrl())
//                                .build();
//                        portfolioResList.add(portfolioRes);
//                    }
//                    responseBuilder.portfolios(portfolioResList);
//                }
//
//            }
//            if (formCodes.contains("resume_010")) { // 취업우대&병역
//                Optional<PreferentialEmp> resultPreferentialEmp = preferentialEmpRepository.findByResumeIdx(resultResume.getIdx());
//                if (resultPreferentialEmp.isPresent()) {
//                    PreferentialEmp preferentialEmp = resultPreferentialEmp.get();
//                    ReadPreferentialEmpRes preferentialEmpRes = ReadPreferentialEmpRes.builder()
//                            .veterans(preferentialEmp.getVeterans())
//                            .protection(preferentialEmp.getProtection())
//                            .subsidy(preferentialEmp.getSubsidy())
//                            .disability(preferentialEmp.getDisability())
//                            .disabilityDegree(preferentialEmp.getDisabilityDegree())
//                            .military(preferentialEmp.getMilitary())
//                            .militaryClass(preferentialEmp.getMilitaryClass())
//                            .militaryStart(preferentialEmp.getMilitaryStart())
//                            .militaryEnd(preferentialEmp.getMilitaryEnd())
//                            .militaryType(preferentialEmp.getMilitaryType())
//                            .militaryRank(preferentialEmp.getMilitaryRank())
//                            .build();
//                    responseBuilder.preferentialEmp(preferentialEmpRes);
//                }
//            }
//            if (formCodes.contains("resume_011")) { // 자기소개서
//                List<CustomLetter> resultCustomLetter = customLetterRepository.findAllByResumeIdx(resultResume.getIdx());
//                if (!resultCustomLetter.isEmpty()) {
//                    // 자기소개서 맞춤 양식 테이블 조회 (공고 idx로)
//                    List<ReadCustomLetterRes> customLetterResList = new ArrayList<>();
//                    for (CustomLetter customLetter : resultCustomLetter) {
//                        ReadCustomLetterRes customLetterRes = ReadCustomLetterRes.builder()
//                                .title(customLetter.getTitle())
//                                .charNum(customLetter.getCharNum())
//                                .contents(customLetter.getContents())
//                                .build();
//                        customLetterResList.add(customLetterRes);
//                    }
//                    responseBuilder.customLetters(customLetterResList);
//                }
//            }
//            return responseBuilder.codes(formCodes).build();
//        }
//        return responseBuilder.build();
//    }

