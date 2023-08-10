package com.vv.task.images;

import com.google.gson.Gson;
import com.vv.task.images.model.ImageRequestDto;
import com.vv.task.images.model.ImageResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    ImageResponseDto responseDto = ImageResponseDto.builder()
            .galContentId(123L)
            .galTitle("title")
            .galWebImageUrl("url")
            .galPhotographer("photographer")
            .build();
    ImageRequestDto requestDto = ImageRequestDto.builder()
            .galContentId(123L)
            .galTitle("title")
            .galWebImageUrl("url")
            .galPhotographer("photographer")
            .build();
    ImageRequestDto invalidateRequestDto = ImageRequestDto.builder()
            .galContentId(119L)
            .galTitle(" ")
            .galWebImageUrl(null)
            .galPhotographer(null)
            .build();

    @Test
    void 컨텐트아이디로_이미지DTO를_겟한다() throws Exception {
        // given
        given(imageService.findByGalContentId(contentId))
                .willReturn(responseDto);

        // when 
        mockMvc.perform(
                        get("/api/v1/images/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.galContentId").exists())
                .andExpect(jsonPath("$.data.galTitle").exists())
                .andExpect(jsonPath("$.data.galWebImageUrl").exists())
                .andExpect(jsonPath("$.data.galPhotographer").exists())
                .andDo(print());

        // then
        verify(imageService).findByGalContentId(contentId);
    }

    @Test
    void RequestDto를_받아서_이미지를_저장하면_ResponseDto가_나온다() throws Exception {
        // given
        given(imageService.create(requestDto)).willReturn(responseDto);

        Gson gson = new Gson();
        String content = gson.toJson(requestDto);

        // when
        mockMvc.perform(
                        post("/api/v1/images")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.galContentId").exists())
                .andExpect(jsonPath("$.data.galTitle").exists())
                .andExpect(jsonPath("$.data.galWebImageUrl").exists())
                .andExpect(jsonPath("$.data.galPhotographer").exists())
                .andDo(print());

        // then
        verify(imageService).create(requestDto);
    }

    @Test
    void CREATE시_밸리데이션_테스트() throws Exception {
        // given
        given(imageService.create(invalidateRequestDto)).willReturn(responseDto);

        Gson gson = new Gson();
        String content = gson.toJson(invalidateRequestDto);

        // when
        mockMvc.perform(
                        post("/api/v1/images")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        // then
    }
}
