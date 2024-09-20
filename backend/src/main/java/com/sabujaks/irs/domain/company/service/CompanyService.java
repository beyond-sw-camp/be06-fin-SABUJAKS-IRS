package com.sabujaks.irs.domain.company.service;

import com.sabujaks.irs.domain.auth.model.entity.CompanyVerify;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.repository.CompanyVerifyRepository;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.company.model.entity.Company;
import com.sabujaks.irs.domain.company.model.entity.CompanyBenefits;
import com.sabujaks.irs.domain.company.model.entity.CompanyImg;
import com.sabujaks.irs.domain.company.model.request.CompanyCreateReq;
import com.sabujaks.irs.domain.company.model.response.CompanyCreateRes;
import com.sabujaks.irs.domain.company.repository.CompanyBenefitsRepository;
import com.sabujaks.irs.domain.company.repository.CompanyImgRepository;
import com.sabujaks.irs.domain.company.repository.CompanyRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CompanyService {
    private final RecruiterRepository recruiterRepository;
    private final CompanyRepository companyRepository;
    private final CompanyImgRepository companyImgRepository;
    private final CompanyBenefitsRepository companyBenefitsRepository;
    private final CompanyVerifyRepository companyVerifyRepository;

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
    public void saveCompanyImages(List<String> fileUrlList) {
        if (fileUrlList != null && !fileUrlList.isEmpty()) {
            for (String fileUrl : fileUrlList) {
                // 각 URL에 대해 CompanyImg 객체 생성
                CompanyImg companyImg = CompanyImg.builder()
                        .url(fileUrl)
                        .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) // 현재 시간 저장
                        .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) // 현재(수정) 시간 저장
                        .build();

                companyImgRepository.save(companyImg);
            }
        }
    }
}