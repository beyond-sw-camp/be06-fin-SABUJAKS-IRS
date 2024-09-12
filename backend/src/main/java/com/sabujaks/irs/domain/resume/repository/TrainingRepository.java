package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
