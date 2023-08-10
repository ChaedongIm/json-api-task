package com.vv.task.images;

import com.vv.task.images.model.Image;
import com.vv.task.images.model.ImageRequestDto;
import com.vv.task.images.model.ImageResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {ImageService.class, ImageRepository.class,})
class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @MockBean
    ImageRepository imageRepository;

    ImageRequestDto requestDto = ImageRequestDto.builder()
            .galContentId(123L)
            .galPhotographer("photoGrapher")
            .galWebImageUrl("http://www.url.com/test")
            .galTitle("title")
            .build();
    ImageResponseDto selectedResponseDto = ImageResponseDto.builder()
            .galContentId(123L)
            .galPhotographer("selectedPhotoGrapher")
            .galWebImageUrl("http://www.url.com/test/selected")
            .galTitle("selectedTitle")
            .build();

    @Test
    void read_findByContentId_테스트() throws Exception {
        // given
        given(imageRepository.findByContentId(requestDto.getGalContentId()))
                .willReturn(Optional.ofNullable(Image.from(requestDto)));

        // when
        ImageResponseDto resultDto = imageService.findByGalContentId(123L);

        // then
        assertEquals(resultDto.getGalContentId(), requestDto.getGalContentId());
        assertEquals(resultDto.getGalPhotographer(), requestDto.getGalPhotographer());
        assertEquals(resultDto.getGalWebImageUrl(), requestDto.getGalWebImageUrl());
        assertEquals(resultDto.getGalTitle(), requestDto.getGalTitle());

        verify(imageRepository).findByContentId(requestDto.getGalContentId());
    }


    @Test
    void create_테스트() throws Exception {
        // given
        Image image = Image.from(requestDto);
        given(imageRepository.save(any()))
                .willReturn(image);

        // when
        ImageResponseDto imageResponseDto = imageService.create(requestDto);

        // then
        assertEquals(imageResponseDto.getGalTitle(), requestDto.getGalTitle());
        assertEquals(imageResponseDto.getGalContentId(), requestDto.getGalContentId());
        assertEquals(imageResponseDto.getGalPhotographer(), requestDto.getGalPhotographer());
        assertEquals(imageResponseDto.getGalWebImageUrl(), requestDto.getGalWebImageUrl());

        verify(imageRepository).save(any());
    }

    @Test
    void update_테스트() throws Exception {
        // given
        Image selectedImage = Image.from(selectedResponseDto);
        given(imageRepository.findByContentId(123L))
                .willReturn(Optional.of(selectedImage));

        // when
        ImageResponseDto imageResponseDto = imageService.update(123L, requestDto);

        // then
        assertEquals(imageResponseDto.getGalContentId(), requestDto.getGalContentId());
        assertEquals(imageResponseDto.getGalTitle(), requestDto.getGalTitle());
        assertEquals(imageResponseDto.getGalPhotographer(), requestDto.getGalPhotographer());
        assertEquals(imageResponseDto.getGalWebImageUrl(), requestDto.getGalWebImageUrl());
    }
}
