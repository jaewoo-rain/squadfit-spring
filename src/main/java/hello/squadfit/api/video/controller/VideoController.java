package hello.squadfit.api.video.controller;

import hello.squadfit.api.video.request.SaveVideoRequest;
import hello.squadfit.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService videoService;

    // 비디오 서버에서 저장하기
    @PostMapping("/{memberId}/{recordId}")
    public ResponseEntity<Long> saveByServer(
            @PathVariable("memberId") Long memberId,
            @PathVariable("recordId") Long recordId,
            @RequestBody SaveVideoRequest request
    ){
        Long result = videoService.saveByServer(memberId, recordId, request);

        return ResponseEntity.ok(result);
    }


}
