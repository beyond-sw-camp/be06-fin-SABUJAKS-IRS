package com.example.common.domain.announcement.repository.querydsl;

import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.search.model.request.SearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementDslRepository {
    Page<Announcement> search(Pageable pageable, SearchReq searchReq);
}
