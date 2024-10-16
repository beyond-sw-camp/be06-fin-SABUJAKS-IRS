package com.service.resume.communication;

import com.service.common.base.BaseResponse;
import com.service.common.dto.feign.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "resumeFeignClient", url = "http://localhost:8080")
public interface ResumeFeignClient {

    @GetMapping("/api/announcement/read/form")
    ReadCustomFormRes readAllCustomFormRes(@RequestParam("announcementIdx") Long announcementIdx);

    @GetMapping("/api/announcement/read/internal")
    ReadAnnouncementRes readAnnouncement(@RequestParam Long announcementIdx);
}
