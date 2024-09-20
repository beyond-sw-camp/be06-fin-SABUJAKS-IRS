package com.sabujaks.irs.domain.announcement.repository;

import com.sabujaks.irs.domain.announcement.model.entity.CustomLetterForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomLetterFormRepository extends JpaRepository<CustomLetterForm, Long> {
}
