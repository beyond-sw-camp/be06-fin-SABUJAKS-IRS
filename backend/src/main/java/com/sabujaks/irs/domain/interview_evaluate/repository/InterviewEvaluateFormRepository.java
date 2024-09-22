package com.sabujaks.irs.domain.interview_evaluate.repository;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluate;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluateForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface InterviewEvaluateFormRepository extends JpaRepository<InterviewEvaluateForm, Long> {
    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcement.uuid = :announcementUUID")
    Optional<InterviewEvaluateForm> findByAnnouncementUUID(String announcementUUID);
    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcement.idx = :announceIdx")
    Optional<InterviewEvaluateForm> findByAnnounceIdx(Long announceIdx);
}
