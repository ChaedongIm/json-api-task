package com.vv.task.images;

import com.vv.task.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/api/v1/images")
    public CommonResponse<Object> getHello() {
        var data = imageService.get();
        
        return CommonResponse.success(data);
    }

}
