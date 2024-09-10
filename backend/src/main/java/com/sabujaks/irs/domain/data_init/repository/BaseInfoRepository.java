package com.sabujaks.irs.domain.data_init.repository;

import com.sabujaks.irs.domain.data_init.entity.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseInfoRepository extends JpaRepository<BaseInfo, Long> {
}
