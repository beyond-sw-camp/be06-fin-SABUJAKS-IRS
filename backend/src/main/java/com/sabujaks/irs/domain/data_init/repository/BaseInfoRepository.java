package com.sabujaks.irs.domain.data_init.repository;

import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;

@Repository
public interface BaseInfoRepository extends JpaRepository<BaseInfo, Long> {
    List<BaseInfo> findAllByGroupName(String groupName);

    @Query("SELECT b FROM BaseInfo b WHERE b.groupName = :groupName AND b.code IN :codes")
    List<BaseInfo> findAllByGroupNameAndCodeIn(@Param("groupName") String groupName, @Param("codes") List<String> codes);

    List<BaseInfo> findByCodeIn(List<String> companyBenefitsCodes);

    PatternSyntaxException findByParentCode(String parentCode);

    // 특정 그룹의 기준 코드 목록 찾기
    List<BaseInfo> findByGroupName(String groupName);

    // 특정 그룹명과 parent_code로 기준 코드 찾기
    List<BaseInfo> findByGroupNameAndParentCode(String groupName, String parentCode);

    // 코드로 엔티티 찾기
    BaseInfo findByCode(String code);

    // parent_code가 NULL인 기준 코드 (대분류 항목) 찾기
    List<BaseInfo> findByGroupNameAndParentCodeIsNull(String groupName);
}