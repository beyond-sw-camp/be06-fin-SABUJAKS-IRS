package com.sabujaks.irs.domain.data_init.repository;

import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseInfoRepository extends JpaRepository<BaseInfo, Long> {
    List<BaseInfo> findAllByGroupName(String groupName);

    @Query("SELECT b FROM BaseInfo b WHERE b.groupName = :groupName AND b.code IN :codes")
    List<BaseInfo> findAllByGroupNameAndCodeIn(@Param("groupName") String groupName, @Param("codes") List<String> codes);
}
