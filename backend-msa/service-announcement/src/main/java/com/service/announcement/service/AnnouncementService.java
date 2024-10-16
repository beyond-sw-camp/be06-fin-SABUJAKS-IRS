package com.service.announcement.service;


import com.service.announcement.communication.AnnouncementFeignClient;
import com.service.announcement.entity.*;
import com.service.common.dto.request.announcement.CreateAnnouncementReq;
import com.service.common.dto.request.announcement.CreateCompanyReq;
import com.service.common.dto.request.announcement.CreateCustomFormReq;
import com.service.announcement.repository.*;
import com.service.common.base.BaseException;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.*;
import com.service.common.dto.response.announcement.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CustomResumeFormRepository customResumeFormRepository;
    private final CustomLetterFormRepository customLetterFormRepository;
    private final BaseInfoRepository baseInfoRepository;
    private final AnnouncementFeignClient announcementFeignClient;
    private final CompanyRepository companyRepository;
    private final CompanyImgRepository companyImgRepository;
    private final CompanyBenefitsRepository companyBenefitsRepository;

    @Transactional
    public CreateAnnouncementRes createStep1(Long memberIdx, String memberRole, String fileUrl, CreateAnnouncementReq dto) throws BaseException {
        if (!Objects.equals(memberRole, "ROLE_RECRUITER")) { // 채용 담당자만 공고를 작성
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }
        if (dto.getSelectForm()) { // 셀렉트 폼이 트루면 = 파일url과 일부 정보 공고 엔티티에 저장
            Announcement announcement = Announcement.builder()
                    .recruiterIdx(memberIdx)
                    .selectForm(dto.getSelectForm()) // 셀렉트 폼
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
            return CreateAnnouncementRes.builder().announcementIdx(announcement.getIdx()).build();
        } else { // 셀렉트 폼이 거짓면 = 파일url이 없으면 들어온 dto만 저장
            Announcement announcement = Announcement.builder()
                    .recruiterIdx(memberIdx)
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
            return CreateAnnouncementRes.builder().announcementIdx(announcement.getIdx()).build();
        }
    }

    @Transactional
    public CreateCustomFormRes createStep2(Long memberIdx, String memberRole, CreateCustomFormReq dto) throws BaseException {
        // TODO:
        // 클라이언트에서 넣을 폼을 선택 -> dto에 그 폼의 코드값이 들어옴
        if (!Objects.equals(memberRole, "ROLE_RECRUITER")) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }
        // 예외 처리) 공고가 잘 저장되어 있는지 먼저 확인 필요
        Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnouncementIdx(dto.getAnnouncementIdx());
        if (resultAnnouncement.isEmpty()) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_NOT_FOUND);
        }
        // 예외 처리) 공고 step2를 다른 채용담당자가 작성할 수 있는지 확인
        if (!Objects.equals(resultAnnouncement.get().getRecruiterIdx(), memberIdx)) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_INVALID_REQUEST);
        }
        // 예외 처리) 해당 공고에 이미 커스텀폼이 있는지 확인
        List<CustomResumeForm> resultCustomResumeForm = customResumeFormRepository.findAllByAnnouncementIdx(dto.getAnnouncementIdx());
        if (!resultCustomResumeForm.isEmpty()) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_TWO_FAIL_ALREADY);
        }
        // 1. 리스트 길이만큼 커스텀 폼 엔티티 생성 후 저장
        List<String> formCodes = dto.getCode();
        CustomResumeForm.CustomResumeFormBuilder builder = setCodes(dto.getCode()).announcement(resultAnnouncement.get());
        customResumeFormRepository.save(builder.build());
        // 2. baseInfo 조회 - groupName과 code 리스트로 조회
        String groupName = "custom_form";
        List<BaseInfo> resultBaseInfoList = baseInfoRepository.findAllByGroupNameAndCodeIn(groupName, formCodes);

        // 3. 저장 배열 생성 (인덱스와 설명 리스트)
        List<Long> emsiIdx = resultBaseInfoList.stream().map(BaseInfo::getIdx).collect(Collectors.toList());
        List<String> emsiDes = resultBaseInfoList.stream().map(BaseInfo::getDescription).collect(Collectors.toList());

        // 4. 추가한 자기소개서 문항 등록 (리스트 길이만큼 커스텀 레터 폼 엔티티 생성 후 저장)
        List<Long> emsiLetterIdx = new ArrayList<>();
        for (int i = 0; i < dto.getTitleList().size(); i++) {
            CustomLetterForm customLetterForm = CustomLetterForm.builder()
                    .title(dto.getTitleList().get(i))
                    .chatLimit(dto.getChatLimitList().get(i))
                    .announcement(resultAnnouncement.get())
                    .build();
            customLetterFormRepository.save(customLetterForm);
            emsiLetterIdx.add(customLetterForm.getIdx());
        }
        // 5. 응답 생성
        return CreateCustomFormRes.builder()
                .baseInfoIdxList(emsiIdx)
                .descriptionList(emsiDes)
                .customFormLetterIdList(emsiLetterIdx)
                .build();
    }

    public ReadAnnouncementRes read(Long announcementIdx) throws BaseException {
        // 공고 찾기
        Announcement announcement = announcementRepository.findByAnnouncementIdx(announcementIdx)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_READ_FAIL));
        // 기업 찾기
        Company company = companyRepository.findByRecruiterIdx(announcement.getRecruiterIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER));
        // 카테고리 문자로 변환
        List<String> addJobCategorys = new ArrayList<>();
        if (announcement.getJobCategory() != null) {
            for (ReadBaseInfoRes bir : readBaseInfo(announcement.getJobCategory())) {
                addJobCategorys.add(bir.getGroupName() + " > " + bir.getDescription());
            }
        }
        String jobCategoryString = String.join(", ", addJobCategorys);
        return ReadAnnouncementRes.builder()
                .announcementIdx(announcementIdx)
                .announcementUUID(announcement.getUuid())
                .companyIdx(company.getIdx())
                .announcementTitle(announcement.getTitle())
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
                .recruiterEmail(announcement.getManagerName())
                .recruiterContact(announcement.getManagerContact())
                .recruiterEmail(announcement.getManagerEmail())
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
                .companyBenefitsDataList(readCompanyBenefit(announcement.getRecruiterIdx()).getBenefitsDataList())
                .build();
    }

    public ReadAnnouncementRes readInternal(Long announcementIdx) throws BaseException {
        // 공고 찾기
        Announcement announcement = announcementRepository.findByAnnouncementIdx(announcementIdx)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_READ_FAIL));
        // 기업 찾기
        Company company = companyRepository.findByRecruiterIdx(announcement.getRecruiterIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER));
        // 카테고리 문자로 변환
        List<String> addJobCategorys = new ArrayList<>();
        if (announcement.getJobCategory() != null) {
            for (ReadBaseInfoRes bir : readBaseInfo(announcement.getJobCategory())) {
                addJobCategorys.add(bir.getGroupName() + " > " + bir.getDescription());
            }
        }
        String jobCategoryString = String.join(", ", addJobCategorys);
        return ReadAnnouncementRes.builder()
                .announcementIdx(announcementIdx)
                .announcementUUID(announcement.getUuid())
                .companyIdx(company.getIdx())
                .announcementTitle(announcement.getTitle())
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
                .recruiterEmail(announcement.getManagerName())
                .recruiterContact(announcement.getManagerContact())
                .recruiterEmail(announcement.getManagerEmail())
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
                .companyBenefitsDataList(readCompanyBenefit(announcement.getRecruiterIdx()).getBenefitsDataList())
                .build();
    }

    public ReadCustomFormRes readForm(Long announcementIdx) throws BaseException {
        CustomResumeForm resultCustomResumeForm = customResumeFormRepository.findByAnnouncementIdx(announcementIdx)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_READ_CUSTOM_FORM_FAIL));
        // 저장된 폼이 있으면, 폼 코드로 설명 찾아서 저장
        List<String> formCodes = getCodes(resultCustomResumeForm);
        List<String> customResumeFormList = new ArrayList<>();
        for(String formCode : formCodes){
            customResumeFormList.add((baseInfoRepository.findByCode(formCode)).getDescription());
        }
        // 자기소개서도 저장
        List<CustomLetterForm> resultLetterForm = customLetterFormRepository.findAllByAnnouncementIdx(announcementIdx);
        List<String> customLetterList = new ArrayList<>();
        for (CustomLetterForm customLetterForm : resultLetterForm) {
            customLetterList.add(customLetterForm.getTitle() + " / " + customLetterForm.getChatLimit());
        }
        return ReadCustomFormRes.builder()
                .customResumeFormList(customResumeFormList)
                .customLetterList(customLetterList)
                .build();
    }

    public List<Read1AnnouncementRes> readAll1() throws BaseException {
        List<Announcement> resultAnnouncementList = announcementRepository.findAll();
        List<Read1AnnouncementRes> resultReadAllResList = new ArrayList<>();
        for (Announcement am : resultAnnouncementList) {
            // 기업정보 가져오기
            Company company = companyRepository.findByRecruiterIdx(am.getRecruiterIdx())
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER));
            // 기업 이미지 url 리스트 넣기
            List<String> imgList = new ArrayList<>();
            for (CompanyImg ci : company.getCompanyImgList()) {
                imgList.add(ci.getUrl());
            }
            resultReadAllResList.add(Read1AnnouncementRes.builder()
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

    @Transactional
    public Page<Read2AnnouncementRes> readAll2(Long memberIdx, Integer page, Integer size) throws BaseException {
        // 공고 테이블 조회 (페이징 처리)
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> resultAnnouncements = announcementRepository.findByRecruiterIdx(memberIdx, pageable);
        if (!resultAnnouncements.hasContent()) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_READ_FAIL);
        }
        List<Read2AnnouncementRes> announcementReadAllRes3List = new ArrayList<>();
        for (Announcement announcement : resultAnnouncements) {
            Read2AnnouncementRes read2AnnouncementRes = Read2AnnouncementRes.builder()
                    .announcementIdx(announcement.getIdx())
                    .announcementTitle(announcement.getTitle())
                    .announcementStart(announcement.getAnnouncementStart())
                    .announcementEnd(announcement.getAnnouncementEnd())
                    .careerBase(announcement.getCareerBase())
                    .build();
            announcementReadAllRes3List.add(read2AnnouncementRes);
        }
        return new PageImpl<>(announcementReadAllRes3List, pageable, resultAnnouncements.getTotalElements());
    }

    public List<Read3AnnouncementRes> readAll3(Long memberIdx, String memberEmail, String memberRole, String careerBase, Integer pageNum) throws BaseException {
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "idx"));
        ReadMemberRes readMemberRes = announcementFeignClient.read(memberEmail, memberRole);
        Page<Announcement> result;
        // 공고 리스트
        if (careerBase.equals("전체")) {
            result = announcementRepository.findAllByRecruiterIdx(memberIdx, pageable);
        } else {
            result = announcementRepository.findAllByRecruiterIdxAndCareerBase(memberIdx, careerBase, pageable);
        }
        List<Read3AnnouncementRes> announcementList = new ArrayList<>();
        for(Announcement announcement : result) {
            // TODO: 해당 공고에 생성된 인터뷰 일정 횟수? 중요도: 낮음
//            Integer countInterviewSchedule = interviewScheduleRepository.countInterviewScheduleByAnnouncementIdx(announcement.getIdx());
//            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByAnnouncementIdx(announcement.getIdx()).get();
//            Integer countReSchedule = 0;
//            if(careerBase.equals("전체")) {
//                for(InterviewSchedule interviewSchedule : interviewScheduleList) {
//                    countReSchedule += reScheduleRepository.countReScheduleByInterviewIdx(interviewSchedule.getIdx());
//                }
//            }
        announcementList.add(Read3AnnouncementRes.builder()
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
//                .countInterviewSchedule(countInterviewSchedule)
//                .countReSchedule(countReSchedule)
                .readRecruiterRes(ReadRecruiterRes.builder()
                        .email(readMemberRes.getEmail())
                        .name(readMemberRes.getName())
                        .build())
                .build());
    }
        return announcementList;
    }

    public CreateCompanyRes createCompany(Long recruiterIdx, String memberEmail, String memberRole, List<String> fileUrls, CreateCompanyReq dto) throws BaseException {
        if(!Objects.equals(memberRole, "ROLE_RECRUITER")){
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }
        // 기업 인증 테이블에서 채용담당자 이메일로 기업에 대한 필요한 3가지 조회
        ReadVerificationRes readVerificationRes = announcementFeignClient.readVerification(memberEmail, true);
        // 기업 정보 등록
        Company company = Company.builder()
                .recruiterIdx(recruiterIdx)
                .ceoName(readVerificationRes.getCeoName())
                .crn(readVerificationRes.getCrn())
                .openedAt(readVerificationRes.getOpenedAt())
                .industry(dto.getIndustry())
                .name(dto.getName())
                .type(dto.getType())
                .companyInfo(dto.getCompanyInfo())
                .capital(dto.getCapital())
                .totalEmp(dto.getTotalEmp())
                .establishDate(dto.getEstablishDate())
                .sales(dto.getSales())
                .business(dto.getBusiness())
                .url(dto.getUrl())
                .address(dto.getAddress())
                .recruiterEmail(readVerificationRes.getRecruiterEmail())
                .build();
        companyRepository.save(company);
        // 리스트 길이만큼 복리후생 엔티티 생성 후 저장
        for(int i=0; i < dto.getCompanyBenefitsList().size(); i++) {
            CompanyBenefits companyBenefits = CompanyBenefits.builder()
                    .code(dto.getCompanyBenefitsList().get(i))
                    .company(company)
                    .build();
            companyBenefitsRepository.save(companyBenefits);
        }
        if (fileUrls != null && !fileUrls.isEmpty()) {
            for (String fileUrl : fileUrls) {
                // 각 URL에 대해 CompanyImg 객체 생성
                CompanyImg companyImg = CompanyImg.builder()
                        .company(company)
                        .url(fileUrl)
                        .build();
                companyImgRepository.save(companyImg);
            }
        }
        return CreateCompanyRes.builder().companyIdx(company.getIdx()).build();
    }

    public ReadCompanyRes readCompany(Long memberIdx, String memberEmail, String memberRole) throws BaseException {
        ReadVerificationRes readVerificationRes = announcementFeignClient.readVerification(memberEmail, true);
        // 기업을 등록했었는지 확인
        Optional<Company> resultCompany = companyRepository.findByRecruiterIdx(memberIdx);
        if(resultCompany.isPresent()) {
            // 만약 기업등록이 이미 됐다면, 있는 정보 보내주기
            Company company = resultCompany.get();
            // companyIdx를 기준으로 이미지 URL 리스트 조회
            List<CompanyImg> companyImages = companyImgRepository.findByCompanyIdx(company.getIdx());
            List<String> imgUrlList = new ArrayList<>();
            for (CompanyImg companyImg : companyImages) { // 각 이미지 객체에서 URL을 추출하여 리스트에 추가
                imgUrlList.add(companyImg.getUrl());
            }
            // 기업 복리후생 리스트 조회
            ReadCompanyBenefitRes readCompanyBenefitRes = readCompanyBenefit(memberIdx);
            // 등록된 기업 정보를 반환
            return ReadCompanyRes.builder()
                    .saved("Y")  // 이미 기업이 등록되어 있다는 표시
                    .companyIdx(company.getIdx())
                    .ceoName(readVerificationRes.getCeoName())  // 기업이 없어도 불러오는 필수 정보
                    .crn(readVerificationRes.getCrn())          // 사업자 등록 번호
                    .openedAt(readVerificationRes.getOpenedAt()) // 개업일자
                    .industry(company.getIndustry())
                    .name(company.getName())
                    .type(company.getType())
                    .companyInfo(company.getCompanyInfo())
                    .capital(company.getCapital())
                    .totalEmp(company.getTotalEmp())
                    .establishDate(company.getEstablishDate())
                    .sales(company.getSales())
                    .business(company.getBusiness())
                    .homeUrl(company.getUrl())
                    .address(company.getAddress())
                    .imgUrlList(imgUrlList)
                    .companyBenefitsList(readCompanyBenefitRes.getBenefitsDataList())
                    .build();
        } else {
            return ReadCompanyRes.builder() // 등록된 기업이 없다면 세가지 정보만 보내주기
                    .saved("N")  // 기업 정보가 없음을 표시
                    .ceoName(readVerificationRes.getCeoName())  // 기업이 없어도 불러오는 필수 정보
                    .crn(readVerificationRes.getCrn())          // 사업자 등록 번호
                    .openedAt(readVerificationRes.getOpenedAt()) // 개업일자
                    .imgUrlList(new ArrayList<>())  // 빈 리스트 반환
                    .companyBenefitsList(new ArrayList<>())  // 빈 리스트 반환
                    .build();
        }
    }

    public ReadCompanyBenefitRes readCompanyBenefit(Long memberIdx) throws BaseException {
        // 채용담당자가 등록한 기업 확인
        Company resultCompany = companyRepository.findByRecruiterIdx(memberIdx)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER));
        // 기업 복리후생 테이블에서 해당 기업의 복리후생 코드들을 가져옴
        List<String> companyBenefitsCodes = companyBenefitsRepository.findAllByCompanyIdx(resultCompany.getIdx())
                .stream().map(CompanyBenefits::getCode).collect(Collectors.toList());
        // 기준 코드 테이블에서 가져온 복리후생 코드를 조회
        List<BaseInfo> benefitsBaseInfos = baseInfoRepository.findByCodeIn(companyBenefitsCodes);
        // 대분류와 소분류를 저장할 맵
        Map<String, List<String>> benefitsMap = new HashMap<>();
        // 기준 코드 테이블에서 응답 생성
        for (BaseInfo baseInfo : benefitsBaseInfos) {
            String category = baseInfo.getDescription();
            String parentCode = baseInfo.getParentCode();
            if (parentCode == null) {
                // 부모 코드가 없으면 대분류로 처리
                benefitsMap.putIfAbsent(category, new ArrayList<>());
            } else {
                // 부모 코드가 있으면 대분류의 소분류에 추가
                String parentCategory = baseInfoRepository.findByCode(parentCode).getDescription();
                benefitsMap.computeIfAbsent(parentCategory, k -> new ArrayList<>()).add(category);
            }
        }
        // 응답 생성.entrySet() : Map의 각 항목을 Map.Entry 객체(키-값 쌍)로 반환
        List<ReadBenefitCategoryRes> benefitCategories = benefitsMap.entrySet().stream()
                .map(entry -> new ReadBenefitCategoryRes(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return ReadCompanyBenefitRes.builder().benefitsDataList(benefitCategories)
                .build();
    }

    public ReadCompanyRes readCompanyImg(Long companyIdx) throws BaseException {
        // companyIdx를 기준으로 이미지 URL 리스트 조회
        List<CompanyImg> companyImages = companyImgRepository.findByCompanyIdx(companyIdx);
        List<String> imgUrlList = new ArrayList<>();
        for (CompanyImg companyImg : companyImages) { // 각 이미지 객체에서 URL을 추출하여 리스트에 추가
            imgUrlList.add(companyImg.getUrl());
        }
        // 등록된 기업 이미지 반환
        return ReadCompanyRes.builder()
                .companyIdx(companyIdx)
                .imgUrlList(imgUrlList)
                .build();
    }

    public Integer readSize(Long recruiterIdx, String careerBase) {
        Optional<List<Announcement>> result;
        if (careerBase.equals("전체")) {
            result = announcementRepository.findByRecruiterIdx(recruiterIdx);
        } else {
            result = announcementRepository.findByRecruiterIdxAndCareerBase(recruiterIdx, careerBase);
        }
        return result.get().size();
    }

    public List<ReadCategoryRes> readCategory(String keyword) {
        // 대분류 데이터 조회
        // 부모코드가 null이고 조회를 원하는 카테고리명이 코드안에 있을 때
        List<BaseInfo> resultBaseInfoList = baseInfoRepository.findAllByCodeContainingAndParentCodeIsNull(keyword);
        // 대분류 리스트
        List<ReadCategoryRes> categoryResList = new ArrayList<>();
        for(BaseInfo category : resultBaseInfoList) {
            // 대분류의 소분류 데이터 조회
            // 대분류들의 코드를 소분류의 부모코드로 사용
            List<BaseInfo> resultSubBaseInfoList = baseInfoRepository.findAllByParentCode(category.getCode());
            // 소분류 리스트
            List<ReadSubcategoryRes> subcategoryResList = new ArrayList<>();
            // 소분류 데이터를 DTO로 변환
            for (BaseInfo subcategory : resultSubBaseInfoList) {
                subcategoryResList.add(new ReadSubcategoryRes(subcategory.getCode(), subcategory.getDescription()));
            }
            // 대분류 데이터를 DTO로 변환
            categoryResList.add(new ReadCategoryRes(category.getCode(), category.getDescription(), subcategoryResList));
        }
        return categoryResList;
    }

    public List<ReadBaseInfoRes> readBaseInfo(String codes) {
        // codes가 쉼표를 포함하는지 확인하고, 포함되면 split, 그렇지 않으면 단일 코드로 리스트 생성
        List<String> codeList = codes.contains(",") ? Arrays.asList(codes.split(",")) : List.of(codes);
        List<BaseInfo> baseInfoList = baseInfoRepository.findByCodeIn(codeList);
        List<ReadBaseInfoRes> baseInfoReadResList = new ArrayList<>();
        // 검색 결과를 BaseInfoReadRes 리스트로 변환하여 반환
        for (BaseInfo baseInfo : baseInfoList) {
            ReadBaseInfoRes baseInfoReadRes = ReadBaseInfoRes.builder()
                    .code(baseInfo.getCode())
                    .description(baseInfo.getDescription())
                    .groupName(baseInfo.getGroupName())
                    .parentCode(baseInfo.getParentCode())
                    .build();
            baseInfoReadResList.add(baseInfoReadRes);
        }
        return baseInfoReadResList;
    }

//    public Page<Read1AnnouncementRes> search(SearchAnnouncementReq searchAnnouncementReq) throws BaseException {
//        Pageable pageable = PageRequest.of(searchAnnouncementReq.getPage(), searchAnnouncementReq.getSize());
//
//        Page<Announcement> resultAnnouncements = announcementRepository.search(pageable, searchAnnouncementReq);
//
//        if (resultAnnouncements.hasContent()) {
//            List<Read1AnnouncementRes> resultReadAllResList = new ArrayList<>();
//            for (Announcement am : resultAnnouncements) {
//                Company company = companyRepository.findByRecruiterIdx(am.getRecruiterIdx())
//                        .orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER));
//                // 기업 이미지 url 리스트 넣기
//                List<String> imgList = new ArrayList<>();
//                for (CompanyImg ci : company.getCompanyImgList()) {
//                    imgList.add(ci.getUrl());
//                }
//                resultReadAllResList.add(Read1AnnouncementRes.builder()
//                                .announcementIdx(am.getIdx())
//                                .companyName(company.getName())
//                                .companyInfo(company.getCompanyInfo())
//                                .announcementTitle(am.getTitle())
//                                .jobTitle(am.getJobTitle())
//                                .careerBase(am.getCareerBase())
//                                .region(am.getRegion())
//                                .announcementEnd(am.getAnnouncementEnd())
//                                .imgList(imgList)
//                                .build()
//                );
//            }
//            return new PageImpl<>(resultReadAllResList, pageable, resultAnnouncements.getTotalElements());
//        } else {
//            return new PageImpl<>(new ArrayList<>(), pageable, 0); // 빈 페이지 반환
//        }
//    }

    public CustomResumeForm.CustomResumeFormBuilder setCodes (List<String> formCodes) {
        CustomResumeForm.CustomResumeFormBuilder builder = CustomResumeForm.builder();
        for (String formCode : formCodes) {
            if(Objects.equals(formCode, "resume_001")){
                builder.resume001(true);
            } if(Objects.equals(formCode, "resume_002")){
                builder.resume002(true);
            } if(Objects.equals(formCode, "resume_003")){
                builder.resume003(true);
            } if(Objects.equals(formCode, "resume_004")){
                builder.resume004(true);
            } if(Objects.equals(formCode, "resume_005")){
                builder.resume005(true);
            } if(Objects.equals(formCode, "resume_006")){
                builder.resume006(true);
            } if(Objects.equals(formCode, "resume_007")){
                builder.resume007(true);
            } if(Objects.equals(formCode, "resume_008")){
                builder.resume008(true);
            } if(Objects.equals(formCode, "resume_009")){
                builder.resume009(true);
            } if(Objects.equals(formCode, "resume_010")){
                builder.resume010(true);
            } if(Objects.equals(formCode, "resume_011")){
                builder.resume011(true);
            }
        }
        return builder;
    }

    public List<String> getCodes (CustomResumeForm customResumeForm) {
        List<String> formCodes = new ArrayList<>();
        if (customResumeForm.getResume001() != null && customResumeForm.getResume001()) {
                formCodes.add("resume_001");
        } // 인적 사항
        if (customResumeForm.getResume002() != null && customResumeForm.getResume002()) {
                formCodes.add("resume_002");
            } // 경력
            if (customResumeForm.getResume003() != null && customResumeForm.getResume003()) {
                formCodes.add("resume_003");
            } // 인턴·대외활동
            if (customResumeForm.getResume004() != null && customResumeForm.getResume004()) {
                formCodes.add("resume_004");
            } // 교육이수
            if (customResumeForm.getResume005() != null && customResumeForm.getResume005()) {
                formCodes.add("resume_005");
            } // 자격증
            if (customResumeForm.getResume006() != null && customResumeForm.getResume006()) {
                formCodes.add("resume_006");
            } // 수상
            if (customResumeForm.getResume007() != null && customResumeForm.getResume007()) {
                formCodes.add("resume_007");
            } // 해외경험
            if (customResumeForm.getResume008() != null && customResumeForm.getResume008()) {
                formCodes.add("resume_008");
            } // 어학
            if (customResumeForm.getResume009() != null && customResumeForm.getResume009()) {
                formCodes.add("resume_009");
            } // 포트폴리오
            if (customResumeForm.getResume010() != null && customResumeForm.getResume010()) {
                formCodes.add("resume_010");
            } // 취업우대&병역
            if (customResumeForm.getResume011() != null && customResumeForm.getResume011()) {
                formCodes.add("resume_011");
            } // 자기소개서
        return formCodes;
    }
}