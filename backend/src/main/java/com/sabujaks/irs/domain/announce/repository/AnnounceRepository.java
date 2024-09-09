package com.sabujaks.irs.domain.announce.repository;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnnounceRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.idx = :announceIdx")
    Optional<Announcement> findByAnnounceIdx(Long announceIdx);
}
