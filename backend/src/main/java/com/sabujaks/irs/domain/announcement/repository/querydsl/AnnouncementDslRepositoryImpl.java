package com.sabujaks.irs.domain.announcement.repository.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.model.entity.QAnnouncement;
import com.sabujaks.irs.domain.auth.model.entity.QRecruiter;
import com.sabujaks.irs.domain.company.model.entity.QCompany;
import com.sabujaks.irs.domain.search.model.request.SearchReq;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class AnnouncementDslRepositoryImpl implements AnnouncementDslRepository {
    private final JPAQueryFactory queryFactory;
    private final QAnnouncement announcement;
    private final QRecruiter recruiter;
    private final QCompany company;

    public AnnouncementDslRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.announcement = QAnnouncement.announcement;
        this.recruiter = QRecruiter.recruiter;
        this.company = QCompany.company;
    }

    @Override
    public Page<Announcement> search(Pageable pageable, SearchReq searchReq) {
        BooleanExpression predicate = createPredicate(searchReq);

        List<Announcement> announcements = queryFactory
                .selectFrom(announcement)
                .leftJoin(announcement.recruiter, recruiter)
                .leftJoin(company).on(recruiter.idx.eq(company.idx))
                .where(predicate)
                .orderBy(getSortOrder(searchReq)) // 정렬 기준 적용
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(announcement)
                .leftJoin(announcement.recruiter, recruiter)
                .leftJoin(company).on(recruiter.idx.eq(company.idx))
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(announcements, pageable, total);
    }

    // 정렬 기준을 결정하는 메서드
    private OrderSpecifier<?>[] getSortOrder(SearchReq searchReq) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (searchReq.getSort() != null && !searchReq.getSort().isBlank()) {
            if (searchReq.getSort().equalsIgnoreCase("최신등록순")) {
                orders.add(announcement.idx.desc());
            } else if (searchReq.getSort().equalsIgnoreCase("마감임박순")) {
                orders.add(announcement.announcementEnd.asc());
            }
        }

        // 기본 정렬: 공고 idx 오름차순
        if (orders.isEmpty()) {
            orders.add(announcement.idx.asc());
        }

        return orders.toArray(new OrderSpecifier[0]);
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    private BooleanExpression createPredicate(SearchReq searchReq) {
        BooleanExpression predicate = null;

        if (searchReq.getKeyword() != null && !searchReq.getKeyword().isBlank()) {
            predicate = keywordContains(searchReq.getKeyword());
        }

        if (searchReq.getCareerBase() != null) {
            predicate = (predicate == null) ? careerBaseEq(searchReq.getCareerBase()) : predicate.and(careerBaseEq(searchReq.getCareerBase()));
        }

        if (searchReq.getJobCategory() != null && !searchReq.getJobCategory().isBlank()) {
            predicate = (predicate == null) ? jobCategoryContains(searchReq.getJobCategory()) : predicate.and(jobCategoryContains(searchReq.getJobCategory()));
        }

        if (searchReq.getRegion() != null && !searchReq.getRegion().isBlank()) {
            predicate = (predicate == null) ? regionContains(searchReq.getRegion()) : predicate.and(regionContains(searchReq.getRegion()));
        }

        // 마감된 공고 제외 조건 추가
        predicate = (predicate == null) ? announcementEndAfterNow() : predicate.and(announcementEndAfterNow());

        return predicate;
    }

    // 마감된 공고를 제외하는 조건
    private BooleanExpression announcementEndAfterNow() {
        LocalDateTime now = LocalDateTime.now();
        String nowString = now.format(formatter);
        return announcement.announcementEnd.gt(nowString);
    }

    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }

        // 타이틀 및 직무 타이틀 검색
        BooleanExpression titleContains = announcement.title.contains(keyword);
        BooleanExpression jobTitleContains = announcement.jobTitle.contains(keyword);

        // 회사 이름에 따른 서브쿼리
        BooleanExpression companyContains = announcement.recruiter.idx.in(
                JPAExpressions.select(company.recruiter.idx)
                        .from(company)
                        .where(company.name.contains(keyword))
        );

        return titleContains.or(jobTitleContains).or(companyContains);
    }

    // 추가적인 조건 메서드 (예: careerBaseEq, jobCategoryContains, regionContains 구현 필요
    private BooleanExpression careerBaseEq(String careerBase) {
        return careerBase != null ? announcement.careerBase.eq(careerBase) : null;
    }

    private BooleanExpression jobCategoryContains(String jobCategory) {
        if (jobCategory == null || jobCategory.isBlank()) {
            return null;
        }
        return announcement.jobCategory.contains(jobCategory);
    }

    private BooleanExpression regionContains(String region) {
        if (region == null || region.isBlank()) {
            return null;
        }
        return announcement.region.contains(region);
    }

}
