package com.sabujaks.irs.domain.announce.repository;

import com.sabujaks.irs.domain.announce.model.entity.CustomForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFormRepository extends JpaRepository<CustomForm, Long> {
}
