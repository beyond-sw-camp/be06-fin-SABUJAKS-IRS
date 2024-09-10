package com.sabujaks.irs.domain.video_interview.controller;

import com.sabujaks.irs.domain.video_interview.mdoel.request.VideoInterviewRoomCreateReq;
import com.sabujaks.irs.domain.video_interview.mdoel.response.VideoInterviewRoomCreateRes;
import com.sabujaks.irs.domain.video_interview.service.VideoInterviewService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/video-interview")
@RequiredArgsConstructor
public class VideoInterviewController {
    private final VideoInterviewService videoInterviewService;

    @PostMapping("/room/create")
    public ResponseEntity<BaseResponse<VideoInterviewRoomCreateRes>> createVideoInterviewRoom(
        @RequestBody VideoInterviewRoomCreateReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        VideoInterviewRoomCreateRes response = videoInterviewService.createRoom(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_ROOM_CREATE_SUCCESS, response));
    }

}
