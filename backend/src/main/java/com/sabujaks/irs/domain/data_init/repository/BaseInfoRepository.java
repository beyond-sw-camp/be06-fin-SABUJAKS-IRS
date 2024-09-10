package com.sabujaks.irs.domain.data_init.repository;

import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseInfoRepository extends JpaRepository<BaseInfo, Long> {
    List<BaseInfo> findAllByGroupName(String groupName);
}
