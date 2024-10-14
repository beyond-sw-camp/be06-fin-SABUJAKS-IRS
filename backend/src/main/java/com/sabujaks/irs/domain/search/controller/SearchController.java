package com.sabujaks.irs.domain.search.controller;

import com.sabujaks.irs.domain.announcement.model.response.AnnouncementReadAllRes;
import com.sabujaks.irs.domain.search.model.request.SearchReq;
import com.sabujaks.irs.domain.search.service.SearchService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @PostMapping("/announcement")
    public ResponseEntity<BaseResponse<AnnouncementReadAllRes>> search(
            @RequestBody SearchReq dto) throws BaseException {
        // 필터 데이터를 서비스로 전달하여 공고 검색
        Page<AnnouncementReadAllRes> response = searchService.search(dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_SEARCH_SUCCESS, response));
    }
}
