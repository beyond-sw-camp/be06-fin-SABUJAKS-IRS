package com.service.interview.repository;


import com.service.interview.entity.InterviewTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewTeamRepository extends JpaRepository<InterviewTeam, Long> {
    Optional<InterviewTeam> findByIdx(Long teamIdx);
}
