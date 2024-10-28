package com.example.api.domain.search.controller;

import com.example.api.domain.announcement.model.response.AnnouncementReadAllRes;
import com.example.api.domain.search.service.SearchService;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.common.domain.search.model.request.SearchReq;
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

    @GetMapping("/announcement")
    public ResponseEntity<BaseResponse<AnnouncementReadAllRes>> search(
            SearchReq dto) throws BaseException {
        // 필터 데이터를 서비스로 전달하여 공고 검색
        Page<AnnouncementReadAllRes> response = searchService.search(dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ANNOUNCEMENT_SEARCH_SUCCESS, response));
    }

}
