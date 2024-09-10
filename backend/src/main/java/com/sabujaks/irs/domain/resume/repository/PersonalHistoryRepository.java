package com.sabujaks.irs.domain.resume.repository;


import com.sabujaks.irs.domain.resume.model.entity.PersonalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalHistoryRepository extends JpaRepository<PersonalHistory, Long> {
}
