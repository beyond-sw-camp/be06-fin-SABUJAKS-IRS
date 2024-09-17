package com.sabujaks.irs.domain.alarm.repository;

import com.sabujaks.irs.domain.alarm.model.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Optional<List<Alarm>> findBySeekerIdx(Long seekerIdx);
}
