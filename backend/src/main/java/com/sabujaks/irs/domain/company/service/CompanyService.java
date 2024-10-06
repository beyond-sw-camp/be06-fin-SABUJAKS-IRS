package com.sabujaks.irs.domain.company.service;

import com.sabujaks.irs.domain.company.model.response.BenefitCategory;
import com.sabujaks.irs.domain.company.model.response.CompanyInfoReadRes;
import com.sabujaks.irs.domain.auth.model.entity.CompanyVerify;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.repository.CompanyVerifyRepository;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.company.model.entity.Company;
import com.sabujaks.irs.domain.company.model.entity.CompanyBenefits;
import com.sabujaks.irs.domain.company.model.entity.CompanyImg;
import com.sabujaks.irs.domain.company.model.request.CompanyCreateReq;
import com.sabujaks.irs.domain.company.model.response.CompanyCreateRes;
import com.sabujaks.irs.domain.company.model.response.CompanyReadRes;
import com.sabujaks.irs.domain.company.repository.CompanyBenefitsRepository;
import com.sabujaks.irs.domain.company.repository.CompanyImgRepository;
import com.sabujaks.irs.domain.company.repository.CompanyRepository;
import com.sabujaks.irs.domain.system.model.entity.BaseInfo;
import com.sabujaks.irs.domain.system.repository.BaseInfoRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompanyService {
    private final RecruiterRepository recruiterRepository;
    private final CompanyRepository companyRepository;
    private final CompanyImgRepository companyImgRepository;
    private final CompanyBenefitsRepository companyBenefitsRepository;
    private final CompanyVerifyRepository companyVerifyRepository;
    private final BaseInfoRepository baseInfoRepository;

    /*******채용담당자 기업정보 등록***********/
    public CompanyCreateRes createCompany(
            Long recruiterIdx,
            CompanyCreateReq dto) throws BaseException {

        // 채용담당자 확인
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isPresent()) {


            // 기업 인증 테이블에서 채용담당자 이메일로 기업에 대한 필요한 3가지 조회
            Optional<CompanyVerify> resultCompanyVerify = companyVerifyRepository.findByRecruiterEmail(
                    resultRecruiter.get().getEmail());

            if(resultCompanyVerify.isPresent()) {
                // 기업 정보 등록
                Company company = Company.builder()
                        .ceoName(resultCompanyVerify.get().getCeoName())
                        .crn(resultCompanyVerify.get().getCrn())
                        .openedAt(resultCompanyVerify.get().getOpenedAt())
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
                        .recruiter(resultRecruiter.get())
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

                // 응답
                return CompanyCreateRes.builder()
                        .companyIdx(company.getIdx())
                        .build();
            }
        } else {
            // 채용담당자 유저에서 찾을 수 없을 때
            throw new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_RECRUITER);
        }
        return null;
    }

    /*******채용담당자 기업 이미지 등록***********/
    public void saveCompanyImages(List<String> fileUrlList, Long companyIdx) {
//        Optional<Company> resultCompany = companyRepository.findById(companyIdx);
        Company company = Company.builder()
                .idx(companyIdx)
                .build();
        if (fileUrlList != null && !fileUrlList.isEmpty()) {
            for (String fileUrl : fileUrlList) {
                // 각 URL에 대해 CompanyImg 객체 생성
                CompanyImg companyImg = CompanyImg.builder()
                        .company(company)
                        .url(fileUrl)
                        .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) // 현재 시간 저장
                        .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) // 현재(수정) 시간 저장
                        .build();

                companyImgRepository.save(companyImg);
            }
        }
    }

    /*******채용담당자 기업 복리후생 조회 (공고 등록 페이지 클릭시)***********/
    public CompanyInfoReadRes readCompanyInfo(String recruiterEmail) throws BaseException {
        // 채용담당자 확인
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterEmail(recruiterEmail);
        if(resultRecruiter.isPresent()) {
            Recruiter recruiter = resultRecruiter.get();
            // 채용담당자가 등록한 기업 확인
            Optional<Company> resultCompany = companyRepository.findByRecruiterIdx(recruiter.getIdx());
            if (resultCompany.isPresent()) {
                Long companyIdx = resultCompany.get().getIdx();

                // 기업 복리후생 테이블에서 해당 기업의 복리후생 코드들을 가져옴
                // 스트림 - 리스트 내의 각 요소에 대해 반복작업 쉽게 가능
                List<String> companyBenefitsCodes = companyBenefitsRepository
                        .findAllByCompanyIdx(companyIdx)
                        .stream()
                        .map(CompanyBenefits::getCode)
                        .collect(Collectors.toList());

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

                // 응답 생성
                // .entrySet() : Map의 각 항목을 Map.Entry 객체(키-값 쌍)로 반환
                List<BenefitCategory> benefitCategories = benefitsMap.entrySet().stream()
                        .map(entry -> new BenefitCategory(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());

                // 최종 응답 반환
                return CompanyInfoReadRes.builder()
                        .benefitsDataList(benefitCategories)
                        .build();
            } else {
                // 기업을 찾을 수 없을 때
                throw new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_REGISTER);
            }
        } else {
            // 채용담당자 유저에서 찾을 수 없을 때
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_REGISTER_STEP_ONE_FAIL_NOT_RECRUITER);
        }
    }

    /*******기업 이미지 조회***********/
    public CompanyReadRes readCompanyImg(Long companyIdx) throws BaseException {
        // companyIdx를 기준으로 이미지 URL 리스트 조회
        List<CompanyImg> companyImages = companyImgRepository.findByCompanyIdx(companyIdx);
        List<String> imgUrlList = new ArrayList<>();

        for (CompanyImg companyImg : companyImages) {
            imgUrlList.add(companyImg.getUrl()); // 각 이미지 객체에서 URL을 추출하여 리스트에 추가
        }

        // 등록된 기업 이미지 반환
        return CompanyReadRes.builder()
                .companyIdx(companyIdx)
                .imgUrlList(imgUrlList)
                .build();
    }

    /*******채용담당자 기업정보 조회 (마이페이지 입장 시)***********/
    public CompanyReadRes readCompany(CustomUserDetails customUserDetails) throws BaseException {
        Long recruiterIdx = customUserDetails.getIdx();

        // 채용담당자 확인
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isPresent()) {
            // 필수 기업인증 정보 불러오기
            Optional<CompanyVerify> resultCompanyVerify = companyVerifyRepository.findByRecruiterEmail(resultRecruiter.get().getEmail());

            // 기업을 등록했었는지 확인
            Optional<Company> resultCompany = companyRepository.findByRecruiterIdx(recruiterIdx);
            if(resultCompany.isPresent()) {
                // 만약 기업등록이 이미 됐다면, 있는 정보 보내주기
                Company company = resultCompany.get();

                // companyIdx를 기준으로 이미지 URL 리스트 조회
                List<CompanyImg> companyImages = companyImgRepository.findByCompanyIdx(company.getIdx());
                List<String> imgUrlList = new ArrayList<>();

                for (CompanyImg companyImg : companyImages) {
                    imgUrlList.add(companyImg.getUrl()); // 각 이미지 객체에서 URL을 추출하여 리스트에 추가
                }

                // 기업 복리후생 리스트 조회
                CompanyInfoReadRes companyInfoReadRes = readCompanyInfo(resultRecruiter.get().getEmail());

                // 등록된 기업 정보를 반환
                return CompanyReadRes.builder()
                        .saved("Y")  // 이미 기업이 등록되어 있다는 표시
                        .companyIdx(company.getIdx())
                        .ceoName(resultCompanyVerify.get().getCeoName())  // 기업이 없어도 불러오는 필수 정보
                        .crn(resultCompanyVerify.get().getCrn())          // 사업자 등록 번호
                        .openedAt(resultCompanyVerify.get().getOpenedAt()) // 개업일자
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
                        .companyBenefitsList(companyInfoReadRes.getBenefitsDataList())
                        .build();

            } else {
                // 등록된 기업이 없다면 세가지 정보만 보내주기
                return CompanyReadRes.builder()
                        .saved("N")  // 기업 정보가 없음을 표시
                        .ceoName(resultCompanyVerify.get().getCeoName())  // 기업이 없어도 불러오는 필수 정보
                        .crn(resultCompanyVerify.get().getCrn())          // 사업자 등록 번호
                        .openedAt(resultCompanyVerify.get().getOpenedAt()) // 개업일자
//                        .industry("")  // 나머지 정보는 빈 값 또는 null로 설정
//                        .name("")
//                        .type("")
//                        .companyInfo("")
//                        .capital("")
//                        .totalEmp("")
//                        .establishDate("")
//                        .sales("")
//                        .business("")
//                        .homeUrl("")
//                        .address("")
                        .imgUrlList(new ArrayList<>())  // 빈 리스트 반환
                        .companyBenefitsList(new ArrayList<>())  // 빈 리스트 반환
                        .build();
            }
        } else {
            // 채용담당자 유저에서 찾을 수 없을 때
            throw new BaseException(BaseResponseMessage.COMPANY_INFO_FAIL_NOT_RECRUITER);
        }
    }

}