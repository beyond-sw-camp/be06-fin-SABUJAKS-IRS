package com.example.api.domain.auth.service;

import com.example.api.domain.auth.model.request.CompanyVerifyReq;
import com.example.api.domain.auth.model.request.CrnApiReq;
import com.example.api.domain.auth.model.response.CompanyVerifyRes;
import com.example.api.domain.auth.model.response.CrnApiRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.feign_client.CrnApiFeignClient;
import com.example.common.domain.auth.model.entity.CompanyVerify;
import com.example.common.domain.auth.repository.CompanyVerifyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyVerifyService {

    @Value("${api-keys.company-registration-key}")
    private String crnKey;

    private final CrnApiFeignClient crnApiFeignClient;
    private final CompanyVerifyRepository companyVerifyRepository;

    public CompanyVerifyService(CrnApiFeignClient crnApiFeignClient, CompanyVerifyRepository companyVerifyRepository) {
        this.crnApiFeignClient = crnApiFeignClient;
        this.companyVerifyRepository = companyVerifyRepository;
    }

    // TODO: 어드민이 등록한 기준코드테이블에서 해당 기업의 시크릿 코드를 참조해 값을 가져와 비교하는 방식으로 반환
    public CompanyVerifyRes companyVerify(CompanyVerifyReq dto) throws BaseException {
        // 사업자 등록 번호 API 사용
        CrnApiReq.Business business = CrnApiReq.Business.builder()
                .b_no(dto.getCrn())
                .start_dt(dto.getOpened_at())
                .p_nm(dto.getCeo_name())
                .build();
        CrnApiReq crnApiReq = CrnApiReq.builder()
                .businesses(List.of(business))
                .build();
        CrnApiRes crnApiRes = crnApiFeignClient.getCrnInfo(crnKey, crnApiReq);
        System.out.println(crnApiRes.getData().get(0).getValid());
        if (Objects.equals(crnApiRes.getData().get(0).getValid(), "02") || Objects.equals(crnApiRes.getData().get(0).getValid(), "01")){
            Optional<CompanyVerify> resultCompanyVerify = companyVerifyRepository.findByRecruiterEmail(dto.getRecruiter_email());
            if(resultCompanyVerify.isPresent()){
                return CompanyVerifyRes.builder()
                        .recruiter_email(resultCompanyVerify.get().getRecruiterEmail())
                        .auth_success(true)
                        .build();
            }else{
                CompanyVerify companyVerify = CompanyVerify.builder()
                        .crn(dto.getCrn())
                        .openedAt(dto.getOpened_at())
                        .ceoName(dto.getCeo_name())
                        .recruiterEmail(dto.getRecruiter_email())
                        .companySecretCode(dto.getCompany_secret_code()) // 이부분은 admin이 관리해야함
                        .build();
                companyVerifyRepository.save(companyVerify);
                return CompanyVerifyRes.builder()
                        .recruiter_email(companyVerify.getRecruiterEmail())
                        .auth_success(true)
                        .build();
            }
        } else {
            throw new BaseException(BaseResponseMessage.AUTH_COMPANY_VERIFY_FAIL);
        }
    }
}
