package com.vv.task.images;

import com.fasterxml.jackson.databind.JsonNode;
import com.vv.task.common.MapperUtils;
import com.vv.task.images.model.Image;
import com.vv.task.images.model.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
    private final ImageRepository imageRepository;
    private final MapperUtils mapperUtils;

    @Value("${open-api.apikey}")
    private String API_KEY;
    private String MOBILE_OS = "IOS";
    private String MOBILE_APP = "MyApp";
    private String TYPE_JSON = "json";
    private String BASE_URL = "http://apis.data.go.kr";

    private String LIST_URL = "/B551011/PhotoGalleryService1/galleryList1";
    private Integer NUM_OF_ROWS = 50;

    @Transactional
    public Object getImageListFromOpenApi() {
        URI uri = UriComponentsBuilder
                .fromUriString(BASE_URL + LIST_URL)
                .queryParam("serviceKey", API_KEY)
                .queryParam("MobileApp", MOBILE_APP)
                .queryParam("MobileOS", MOBILE_OS)
                .queryParam("_type", TYPE_JSON)
                .queryParam("numOfRows", NUM_OF_ROWS)
                .encode()
                .build()
                .toUri();
        ResponseEntity<JsonNode> response = restTemplate.exchange(uri, HttpMethod.GET, null, JsonNode.class);
        HttpStatus statusCode = response.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
            throw new HttpServerErrorException(statusCode);
        }

        List<Image> images = getImagesFromResponse(response);
        imageRepository.saveAll(images);

        return "Save 성공";
    }

    private List<Image> getImagesFromResponse(ResponseEntity<JsonNode> response) {
        List<Image> images = new ArrayList<>();
        response.getBody()
                .get("response")
                .get("body")
                .get("items")
                .get("item")
                .iterator()
                .forEachRemaining(node -> {
                    ImageResponseDto imageDto = mapperUtils.getDTOFromJson(node, ImageResponseDto.class);
                    images.add(Image.from(imageDto));
                });

        return images;
    }
}
