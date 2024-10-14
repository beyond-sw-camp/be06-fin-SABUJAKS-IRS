package com.sabujaks.irs.domain.announcement.service;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.model.entity.CustomForm;
import com.sabujaks.irs.domain.announcement.model.entity.CustomLetterForm;
import com.sabujaks.irs.domain.announcement.model.request.AnnouncementCreateReq;
import com.sabujaks.irs.domain.announcement.model.request.CustomFormCreateReq;
import com.sabujaks.irs.domain.announcement.model.response.*;
import com.sabujaks.irs.domain.announcement.repository.AnnouncementRepository;
import com.sabujaks.irs.domain.announcement.repository.CustomFormRepository;
import com.sabujaks.irs.domain.announcement.repository.CustomLetterFormRepository;
import com.sabujaks.irs.domain.announcement.repository.querydsl.AnnouncementDslRepository;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.model.response.RecruiterRes;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.company.model.entity.Company;
import com.sabujaks.irs.domain.company.model.entity.CompanyImg;
import com.sabujaks.irs.domain.company.repository.CompanyBenefitsRepository;
import com.sabujaks.irs.domain.company.repository.CompanyRepository;
import com.sabujaks.irs.domain.company.service.CompanyService;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.ReScheduleRepository;
import com.sabujaks.irs.domain.resume.model.entity.Resume;
import com.sabujaks.irs.domain.system.model.entity.BaseInfo;
import com.sabujaks.irs.domain.system.model.response.BaseInfoReadRes;
import com.sabujaks.irs.domain.system.repository.BaseInfoRepository;
import com.sabujaks.irs.domain.resume.repository.ResumeRepository;
import com.sabujaks.irs.domain.system.service.BaseInfoService;
import com.sabujaks.irs.global.common.annotation.ExeTimer;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.sabujaks.irs.domain.company.model.entity.CompanyBenefits;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final RecruiterRepository recruiterRepository;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementDslRepository announcementDslRepository;
    private final CustomFormRepository customFormRepository;
    private final CustomLetterFormRepository letterFormRepository;
    private final BaseInfoRepository baseInfoRepository;
    private final CompanyRepository companyRepository;
    private final ResumeRepository resumeRepository;
    private final CompanyService companyService;
    private final BaseInfoService baseInfoService;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final ReScheduleRepository reScheduleRepository;


    /*******채용담당자 공고 등록 (step1)***********/
    @Transactional
    public AnnouncementCreateRes createAnnouncement(
        CustomUserDetails customUserDetails,
        String fileUrl,
        AnnouncementCreateReq dto) throws BaseException {

        Long recruiterIdx = customUserDetails.getIdx();
        // 채용담당자 확인
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isPresent()) {
            // 셀렉트 폼이 트루면 = 파일url과 일부 정보 공고 엔티티에 저장
            if(dto.getSelectForm()) {
                Announcement announcement = Announcement.builder()
                        .recruiter(resultRecruiter.get())
                        .selectForm(dto.getSelectForm())
                        .title(dto.getTitle()) // 공고제목
                        .jobCategory(dto.getJobCategoryList()) // 모집직무
                        .jobTitle(dto.getJobTitle()) // 모집분야명
                        .recruitedNum(dto.getRecruitedNum()) // 모집인원
                        .careerBase(dto.getCareerBase()) // 신입경력
                        .region(dto.getRegion()) // 근무지역
                        .announcementStart(dto.getAnnouncementStart()) // 시작날
                        .announcementEnd(dto.getAnnouncementEnd()) // 종료날
                        .interviewNum(dto.getInterviewNum()) // 면접횟수
                        .imgUrl(fileUrl)
                        .build();
                announcementRepository.save(announcement);

                // 응답
                return AnnouncementCreateRes.builder()
                        .announcementIdx(announcement.getIdx())
                        .build();

            } else { // 셀렉트 폼이 폴스면 = 파일url이 없으면 들어온 dto만 저장
                Announcement announcement = Announcement.builder()
                        .recruiter(resultRecruiter.get())
                        .selectForm(dto.getSelectForm())
                        .title(dto.getTitle())
                        .jobCategory(dto.getJobCategoryList())
                        .jobTitle(dto.getJobTitle())
                        .recruitedNum(dto.getRecruitedNum())
                        .careerBase(dto.getCareerBase())
                        .intro(dto.getIntro())
                        .positionQuali(dto.getPositionQuali())
                        .jobType(dto.getJobType())
                        .salary(dto.getSalary())
                        .conditions(dto.getConditions())
                        .region(dto.getRegion())
                        .benefits(dto.getBenefits())
                        .managerName(dto.getManagerName())
                        .managerContact(dto.getManagerContact())
                        .managerEmail(dto.getManagerEmail())
                        .process(dto.getProcess())
                        .announcementStart(dto.getAnnouncementStart())
                        .announcementEnd(dto.getAnnouncementEnd())
                        .interviewNum(dto.getInterviewNum())
                        .note(dto.getNote())
                        .build();
                announcementRepository.save(announcement);

                // 응답
                return AnnouncementCreateRes.builder()
                        .announcementIdx(announcement.getIdx())
                        .build();
            }
        } else {
            // 채용담당자 유저에서 찾을 수 없을 때
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }
    }


    /*******채용담당자 지원서 폼 조립 + 자기소개서 문항 등록 (step2)***********/
    @Transactional
    public CustomFormCreateRes createCustomForm(
        CustomUserDetails customUserDetails,
        CustomFormCreateReq dto) throws BaseException {
        // 클라이언트에서 넣을 폼을 선택 -> dto에 그 폼의 코드값이 들어옴

        Long recruiterIdx = customUserDetails.getIdx();
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isEmpty()) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }

        // 예외 처리) 공고가 잘 저장되어 있는지 먼저 확인 필요
        Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnounceIdx(dto.getAnnouncementIdx());
        if (resultAnnouncement.isEmpty()) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_NOT_FOUND);
        }

        // 예외 처리) 해당 공고에 이미 커스텀폼이 있는지 확인
        List<CustomForm> resultCustomForm = customFormRepository.findAllByAnnouncementIdx(dto.getAnnouncementIdx());
        if(!resultCustomForm.isEmpty()) {
            // 이미 폼이 등록된 공고면
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_ALREADY);
        }

        // 1. 리스트 길이만큼 커스텀 폼 엔티티 생성 후 저장
        List<String> formCodes = dto.getCode();
        for (String code : formCodes) {
            CustomForm customForm = CustomForm.builder()
                    .code(code)
                    .announcement(resultAnnouncement.get())
                    .build();
            customFormRepository.save(customForm);
        }

        // 2. baseInfo 조회 - groupName과 code 리스트로 조회
        String groupName = "custom_form";
        List<BaseInfo> resultBaseInfoList = baseInfoRepository.findAllByGroupNameAndCodeIn(groupName, formCodes);

        // 3. 저장 배열 생성 (인덱스와 설명 리스트)
        List<Long> emsiIdx = resultBaseInfoList.stream()
                .map(BaseInfo::getIdx)
                .collect(Collectors.toList());

        List<String> emsiDes = resultBaseInfoList.stream()
                .map(BaseInfo::getDescription)
                .collect(Collectors.toList());

        // 4. 추가한 자기소개서 문항 등록 (리스트 길이만큼 커스텀 레터 폼 엔티티 생성 후 저장)
        List<Long> emsiLetterIdx = new ArrayList<>();
        for (int i = 0; i < dto.getTitleList().size(); i++) {
            CustomLetterForm customLetterForm = CustomLetterForm.builder()
                    .title(dto.getTitleList().get(i))
                    .chatLimit(dto.getChatLimitList().get(i))
                    .announcement(resultAnnouncement.get())
                    .build();
            letterFormRepository.save(customLetterForm);
            emsiLetterIdx.add(customLetterForm.getIdx());
        }

        // 5. 응답 생성
        return CustomFormCreateRes.builder()
                .baseInfoIdxList(emsiIdx)
                .descriptionList(emsiDes)
                .customFormLetterIdList(emsiLetterIdx)
                .build();
    }


    /*******공고 전체 조회***********/
    @ExeTimer
    public List<AnnouncementReadAllRes> readAllSee() throws BaseException {
        List<Announcement> resultAnnouncementList = announcementRepository.findAll();

        List<AnnouncementReadAllRes> resultReadAllResList = new ArrayList<>();
        for (Announcement am : resultAnnouncementList) {
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
        return resultReadAllResList;
    }

    /*******공고 상세 조회***********/
    public AnnouncementReadDetailRes readDetailSee(Long announcementIdx) throws BaseException {
        // 공고 찾기
        Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnounceIdx(announcementIdx);
        if(resultAnnouncement.isPresent()) {
            Announcement announcement = resultAnnouncement.get();
            // 기업 찾기
            Optional<Company> resultCompany = companyRepository.findByRecruiterIdx(announcement.getRecruiter().getIdx());
            if (resultCompany.isPresent()) {
                Company company = resultCompany.get();
                Recruiter resultRecruiter = recruiterRepository.findByRecruiterIdx(company.getRecruiter().getIdx()).get();

                // 카테고리 문자로 변환
                List<String> addJobCategorys = new ArrayList<>();
                if(announcement.getJobCategory() != null) {
                    for(BaseInfoReadRes bir : baseInfoService.getInfo(announcement.getJobCategory())) {
                        addJobCategorys.add(bir.getGroupName()+" > "+bir.getDescription());
                    }
                }
                String jobCategoryString = String.join(", ", addJobCategorys);


                AnnouncementReadDetailRes announcementReadDetailRes = AnnouncementReadDetailRes.builder()
                        .announcementIdx(announcementIdx)
                        .companyIdx(company.getIdx())

                        .title(announcement.getTitle())
                        .selectForm(announcement.getSelectForm())
                        .imgUrl(announcement.getImgUrl())
                        .jobCategory(jobCategoryString)
                        .jobTitle(announcement.getJobTitle())
                        .recruitedNum(announcement.getRecruitedNum())
                        .careerBase(announcement.getCareerBase())
                        .positionQuali(announcement.getPositionQuali())
                        .region(announcement.getRegion())
                        .jobType(announcement.getJobType())
                        .salary(announcement.getSalary())
                        .conditions(announcement.getConditions())
                        .benefits(announcement.getBenefits())
                        .managerName(announcement.getManagerName())
                        .managerContact(announcement.getManagerContact())
                        .managerEmail(announcement.getManagerEmail())
                        .intro(announcement.getIntro())
                        .announcementStart(announcement.getAnnouncementStart())
                        .announcementEnd(announcement.getAnnouncementEnd())
                        .interviewNum(announcement.getInterviewNum())
                        .process(announcement.getProcess())
                        .note(announcement.getNote())

                        .companyIndustry(company.getIndustry())
                        .companyName(company.getName())
                        .companyType(company.getType())
                        .companyInfo(company.getCompanyInfo())
                        .companyCapital(company.getCapital())
                        .companyTotalEmp(company.getTotalEmp())
                        .companyEstablishDate(company.getEstablishDate())
                        .companySales(company.getSales())
                        .companyBusiness(company.getBusiness())
                        .companyUrl(company.getUrl())
                        .companyAddress(company.getAddress())
                        .companyBenefitsDataList(companyService.readCompanyInfo(resultRecruiter.getEmail()).getBenefitsDataList())
                        .build();

                return announcementReadDetailRes;
            } else {
                // 기업을 찾을 수 없을 때
                throw new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER);
            }
        } else {
            // 공고 정보가 없을 때
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL);
        }
    }

    public List<AnnouncementReadAllRes2> readAllAnnouncement(Long recruiterIdx, String careerBase, Integer pageNum) throws BaseException {
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "idx"));

        Optional<Recruiter> recruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        Page<Announcement> result;

        // 공고 리스트
        if(careerBase.equals("전체")) {
            result = announcementDslRepository.findByRecruiterIdx(recruiterIdx, pageable);
        } else {
            result = announcementDslRepository.findByRecruiterIdxAndCareerBase(recruiterIdx, careerBase, pageable);
        }

        List<AnnouncementReadAllRes2> announcementList = new ArrayList<>();
        for(Announcement announcement : result) {
            // 해당 공고에 생성된 인터뷰 일정 리스트



            Integer countInterviewSchedule = interviewScheduleRepository.countInterviewScheduleByAnnouncementIdx(announcement.getIdx());
            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByAnnouncementIdx(announcement.getIdx()).get();
            Integer countReSchedule = 0;
            if(careerBase.equals("전체")) {
                for(InterviewSchedule interviewSchedule : interviewScheduleList) {
                    countReSchedule += reScheduleRepository.countReScheduleByInterviewIdx(interviewSchedule.getIdx());
                }
            }
            announcementList.add(AnnouncementReadAllRes2.builder()
                    .idx(announcement.getIdx())
                    .title(announcement.getTitle())
                    .selectForm(announcement.getSelectForm())
                    .imgUrl(announcement.getImgUrl())
                    .jobCategory(announcement.getJobCategory())
                    .jobTitle(announcement.getJobTitle())
                    .recruitedNum(announcement.getRecruitedNum())
                    .careerBase(announcement.getCareerBase())
                    .positionQuali(announcement.getPositionQuali())
                    .region(announcement.getRegion())
                    .jobType(announcement.getJobType())
                    .salary(announcement.getSalary())
                    .conditions(announcement.getConditions())
                    .benefits(announcement.getBenefits())
                    .announcementStart(announcement.getAnnouncementStart())
                    .announcementEnd(announcement.getAnnouncementEnd())
                    .interviewNum(announcement.getInterviewNum())
                    .process(announcement.getProcess())
                    .note(announcement.getNote())
                    .uuid(announcement.getUuid())
                    .countInterviewSchedule(countInterviewSchedule)
                    .countReSchedule(countReSchedule)
                    .recruiterRes(RecruiterRes.builder()
                            .email(recruiter.get().getEmail())
                            .name(recruiter.get().getName())
                            .build())
                    .build());
        }

        return announcementList;
    }

    public Integer getTotalAnnouncement(Long recruiterIdx, String careerBase) {
        Optional<List<Announcement>> result;
        if(careerBase.equals("전체")) {
            result = announcementRepository.findByRecruiterIdx(recruiterIdx);
        } else {
            result = announcementRepository.findByRecruiterIdxAndCareerBase(recruiterIdx, careerBase);
        }

        return result.get().size();
    }

    @Transactional
    public Page<AnnouncementReadAllRes3> readAllRecruiterAnnouncement(CustomUserDetails customUserDetails, Integer page, Integer size) throws BaseException {
        Long recruiterIdx = customUserDetails.getIdx();
        // 채용담당자 테이블 조회
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isPresent()) {
            // 공고 테이블 조회 (페이징 처리)
            Pageable pageable = PageRequest.of(page, size);
            Page<Announcement> resultAnnouncements = announcementRepository.findByRecruiterIdx(recruiterIdx, pageable);
            if(resultAnnouncements.hasContent()) {
                List<AnnouncementReadAllRes3> announcementReadAllRes3List = new ArrayList<>();
                for(Announcement announcement : resultAnnouncements) {
                    // 공고에 지원한 지원자 수 조회
                    AnnouncementReadAllRes3 announcementReadAllRes3 = AnnouncementReadAllRes3.builder()
                            .announcementIdx(announcement.getIdx())
                            .announcementTitle(announcement.getTitle())
                            .announcementStart(announcement.getAnnouncementStart())
                            .announcementEnd(announcement.getAnnouncementEnd())
                            .careerBase(announcement.getCareerBase())
                            .seekerNum(resumeRepository.countResumesByAnnouncementIdx(announcement.getIdx()))
                            .build();
                    announcementReadAllRes3List.add(announcementReadAllRes3);
                }
                return new PageImpl<>(announcementReadAllRes3List, pageable, resultAnnouncements.getTotalElements());
            } else {
                throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL);
            }
        } else {
            throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_SEEKER);

        }

    }

    // 지원서 폼 조회
    public CustomFormReadAllRes readCustomForm(CustomUserDetails customUserDetails, Long announcementIdx) throws BaseException {
        // 채용담당자 유저가 아닐 때
        Long recruiterIdx = customUserDetails.getIdx();
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isEmpty()) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }

        List<CustomForm> resultCustomForm = customFormRepository.findAllByAnnouncementIdx(announcementIdx);
        if(resultCustomForm.isEmpty()) {
            // 저장된 폼이 없으면
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_READ_CUSTOM_FORM_FAIL);
        }

        // 저장된 폼이 있으면, 폼 코드로 설명 찾아서 저장
        List<String> customFormList = new ArrayList<>();
        for(CustomForm customForm : resultCustomForm) {
            customFormList.add((baseInfoRepository.findByCode(customForm.getCode())).getDescription());
        }

        // 자기소개서도 저장
        List<CustomLetterForm> resultLetterForm = letterFormRepository.findAllByAnnouncementIdx(announcementIdx);

        List<String> customLetterList = new ArrayList<>();
        for(CustomLetterForm customLetterForm : resultLetterForm) {
            customLetterList.add(customLetterForm.getTitle()+" / "+customLetterForm.getChatLimit());
        }

        CustomFormReadAllRes customFormReadAllRes = CustomFormReadAllRes.builder()
                .customFormList(customFormList)
                .customLetterList(customLetterList)
                .build();

        return customFormReadAllRes;
    }
}