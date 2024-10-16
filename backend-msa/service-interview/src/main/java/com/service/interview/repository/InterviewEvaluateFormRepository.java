package com.service.interview.repository;

import com.service.interview.entity.InterviewEvaluateForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface InterviewEvaluateFormRepository extends JpaRepository<InterviewEvaluateForm, Long> {
    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcementUUID = :announcementUUID")
    Optional<InterviewEvaluateForm> findByAnnouncementUUID(String announcementUUID);
    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcementIdx = :announcementIdx")
    Optional<InterviewEvaluateForm> findByAnnouncementIdx(Long announcementIdx);

//    @Query("SELECT ief FROM InterviewEvaluateForm ief WHERE ief.announcment.recruiter.idx = :recruiterIdx")
//    Optional<List<InterviewEvaluateForm>> findAllByAnnounceIdx(Long recruiterIdx );
}
