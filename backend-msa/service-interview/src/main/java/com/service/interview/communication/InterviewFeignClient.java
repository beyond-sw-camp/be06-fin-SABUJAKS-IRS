package com.service.interview.communication;

import com.service.common.base.BaseException;
import com.service.common.dto.feign.ReadAnnouncementRes;
import com.service.common.dto.feign.ReadEstimatorRes;
import com.service.common.dto.feign.ReadSeekerRes;
import com.service.common.dto.feign.ReadSubmissionResumeRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "InterviewFeignClient", url = "http://localhost:8080")
public interface InterviewFeignClient {
    @GetMapping("/api/announcement/read")
    ReadAnnouncementRes readAnnouncement(@RequestParam Long announcementIdx) throws BaseException;

    @GetMapping("/api/resume/read/es-submission")
    ReadSubmissionResumeRes readResume(@RequestParam Long announcementIdx, @RequestParam Long seekerIdx);

    @GetMapping("/api/member/read")
    ReadSeekerRes readSeeker();

    @GetMapping("/api/member/read")
    ReadSeekerRes readSeeker(@RequestParam Long seekerIdx);

    @GetMapping("/api/member/read")
    ReadEstimatorRes readEstimator(@RequestParam String estimatorEmail);

}
