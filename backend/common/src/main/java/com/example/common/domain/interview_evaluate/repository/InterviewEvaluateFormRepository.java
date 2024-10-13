package com.example.common.domain.interview_evaluate.repository;

import com.example.common.domain.interview_evaluate.model.entity.InterviewEvaluateForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;


@EnableJpaRepositories
public interface InterviewEvaluateFormRepository extends JpaRepository<InterviewEvaluateForm, Long> {
    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcement.uuid = :announcementUUID")
    Optional<InterviewEvaluateForm> findByAnnouncementUUID(String announcementUUID);
    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcement.idx = :announcementIdx")
    Optional<InterviewEvaluateForm> findByAnnounceIdx(Long announcementIdx);

//    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcment.recruiter.idx = :recruiterIdx")
//    Optional<List<InterviewEvaluateForm>> findAllByAnnounceIdx(Long recruiterIdx );
}
