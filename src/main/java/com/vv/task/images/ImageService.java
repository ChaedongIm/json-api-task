package com.vv.task.images;

import com.vv.task.model.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ImageService {

    @Value("${open-api.apikey}")
    private String API_KEY;

    public String getAPI_KEY() {
        return API_KEY;
    }

    public CommonResponse<Object> get() {
        String mobileOS = "IOS";
        String mobileApp = "MyApp";
        String block = WebClient.builder()
                .baseUrl("http://apis.data.go.kr").build()
                .get()
                .uri(builder ->
                        builder.path("/B551011/PhotoGalleryService1/galleryList1")
                                .queryParam("serviceKey", API_KEY)
                                .queryParam("MobileApp", mobileApp)
                                .queryParam("MobileOS", mobileOS)
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("block = " + block);
        return CommonResponse.success(block);
    }
}
