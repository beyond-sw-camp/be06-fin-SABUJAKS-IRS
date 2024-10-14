package com.sabujaks.irs.domain.announcement.repository.querydsl;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.search.model.request.SearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface AnnouncementDslRepository {
    Page<Announcement> search(Pageable pageable, SearchReq searchReq);
}
