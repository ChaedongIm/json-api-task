package com.vv.task.images;

import com.vv.task.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/api/v1/list")
    public CommonResponse<?> getList(Pageable pageable) {
        return CommonResponse.success(imageService.findAll(pageable));
    }


}
