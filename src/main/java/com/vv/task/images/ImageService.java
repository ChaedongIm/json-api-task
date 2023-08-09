package com.vv.task.images;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vv.task.images.model.Image;
import com.vv.task.images.model.ImageResponseDto;
import lombok.RequiredArgsConstructor;
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
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final ImageRepository imageRepository;

    @Value("${open-api.apikey}")
    private String API_KEY;
    private String MOBILE_OS = "IOS";
    private String MOBILE_APP = "MyApp";
    private String TYPE_JSON = "json";
    private String BASE_URL = "http://apis.data.go.kr";

    private String LIST_URL = "/B551011/PhotoGalleryService1/galleryList1";
    private Integer NUM_OF_ROWS = 50;

    @Transactional
    public Object getImageList() {
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
        Iterator<JsonNode> iter = response
                .getBody()
                .get("response")
                .get("body")
                .get("items")
                .get("item")
                .iterator();
        while (iter.hasNext()) {
            JsonNode next = iter.next();
            try {
                Image image = Image.from(objectMapper.treeToValue(next, ImageResponseDto.class));
                images.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
