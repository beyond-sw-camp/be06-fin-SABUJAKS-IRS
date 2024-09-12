package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.CustomLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomLetterRepository extends JpaRepository<CustomLetter, Long> {
}
