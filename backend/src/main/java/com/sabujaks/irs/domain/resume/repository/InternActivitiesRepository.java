package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.InternsActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternActivitiesRepository extends JpaRepository<InternsActivity, Long> {
}
