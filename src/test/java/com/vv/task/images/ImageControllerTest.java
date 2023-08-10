package com.vv.task.images;

import com.vv.task.images.model.ImageResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
class ImageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ImageService imageService;

    Long contentId = 123L;
    ImageResponseDto testDto = ImageResponseDto.builder()
            .galContentId(123L)
            .galTitle("title")
            .galWebImageUrl("url")
            .galPhotographer("photographer")
            .build();

    @Test
    void 컨텐트아이디로_이미지DTO를_겟한다() throws Exception {
        // given
        given(imageService.findByGalContentId(contentId))
                .willReturn(testDto);

        // when
        mockMvc.perform(get("/api/v1/images/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.galContentId").exists())
                .andExpect(jsonPath("$.data.galTitle").exists())
                .andExpect(jsonPath("$.data.galWebImageUrl").exists())
                .andExpect(jsonPath("$.data.galPhotographer").exists())
                .andDo(print());

        // then
        verify(imageService).findByGalContentId(contentId);
    }

}
