package com.sabujaks.irs.domain.announce.repository;

import com.sabujaks.irs.domain.announce.model.entity.CustomLetterForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomLetterFormRepository extends JpaRepository<CustomLetterForm, Long> {
}
