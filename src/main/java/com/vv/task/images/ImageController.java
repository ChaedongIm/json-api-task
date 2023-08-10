package com.vv.task.images;

import com.vv.task.common.CommonResponse;
import com.vv.task.images.model.ImageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/api/v1/images/{galContentId}")
    public CommonResponse<?> getImage(@PathVariable Long galContentId) {
        return CommonResponse.success(imageService.findByGalContentId(galContentId));
    }

    @GetMapping("/api/v1/images")
    public CommonResponse<?> getList(Pageable pageable) {
        return CommonResponse.success(imageService.findAll(pageable));
    }

    @PostMapping("/api/v1/images")
    public CommonResponse<?> saveImage(@Validated @RequestBody ImageRequestDto requestDto) {
        return CommonResponse.success(imageService.create(requestDto));
    }

    @PutMapping("/api/v1/images/{galContentId}")
    public CommonResponse<?> updateImage(@PathVariable Long galContentId,
                                         @Validated @RequestBody ImageRequestDto requestDto) {
        return CommonResponse.success(imageService.update(galContentId, requestDto));
    }

    @DeleteMapping("/api/v1/images/{galContentId}")
    public CommonResponse<?> deleteImage(@PathVariable Long galContentId) {
        return CommonResponse.success("삭제 성공 ContendId = " + imageService.delete(galContentId));
    }
}
