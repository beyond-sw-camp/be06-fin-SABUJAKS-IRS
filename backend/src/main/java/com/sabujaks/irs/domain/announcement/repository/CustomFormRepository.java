package com.sabujaks.irs.domain.announcement.repository;

import com.sabujaks.irs.domain.announcement.model.entity.CustomForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFormRepository extends JpaRepository<CustomForm, Long> {
}
