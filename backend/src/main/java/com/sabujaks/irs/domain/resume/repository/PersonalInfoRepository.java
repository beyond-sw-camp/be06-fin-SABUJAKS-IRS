package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
}
