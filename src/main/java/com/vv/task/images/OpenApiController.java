package com.vv.task.images;

import com.vv.task.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping("/api/v1/open-api/list")
    public CommonResponse<?> getListFromOpenApi() {
        return CommonResponse.success(openApiService.getImageListFromOpenApi());
    }
}
