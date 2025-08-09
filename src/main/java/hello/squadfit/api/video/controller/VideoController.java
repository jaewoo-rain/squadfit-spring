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

    @PostMapping("/{memberId}/{recordId}")
    public ResponseEntity<Long> saveByServer(
            @PathVariable Long memberId,
            @PathVariable Long recordId,
            @RequestBody SaveVideoRequest request
    ){
        videoService.saveByServer()
    }


}
