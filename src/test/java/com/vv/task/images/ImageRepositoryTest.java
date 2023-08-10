package com.vv.task.images;

import com.vv.task.images.model.Image;
import com.vv.task.images.model.ImageRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Test
    void 이미지_저장_후_불러오기() throws Exception {
        // given
        ImageRequestDto requestDto = ImageRequestDto.builder()
                .galContentId(123L)
                .galTitle("title")
                .galWebImageUrl("url")
                .galPhotographer("photographer")
                .build();

        // when
        Image savedImage = imageRepository.save(Image.from(requestDto));
        Image image = imageRepository.findByContentId(123L)
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertThat(image.getContentId()).isEqualTo(savedImage.getContentId());
        assertThat(image.getTitle()).isEqualTo(savedImage.getTitle());
        assertThat(image.getPhotographer()).isEqualTo(savedImage.getPhotographer());
        assertThat(image.getImageUrl()).isEqualTo(savedImage.getImageUrl());
    }
}
