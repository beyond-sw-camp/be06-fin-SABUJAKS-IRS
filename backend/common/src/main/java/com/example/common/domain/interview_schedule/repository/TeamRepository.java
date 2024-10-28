package com.example.common.domain.interview_schedule.repository;


import com.example.common.domain.interview_schedule.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByIdx(Long teamIdx);
}
