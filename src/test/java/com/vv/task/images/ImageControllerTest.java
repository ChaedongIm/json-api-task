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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void Image_READ_ContentId를_넣으면_ImageDTO_리턴() throws Exception {
        // given
        given(imageService.findByGalContentId(any()))
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
    void Image_CREATE_RequestDto를_받아_이미지를_저장하면_ResponseDto_리턴() throws Exception {
        // given
        given(imageService.create(any())).willReturn(responseDto);
        Gson gson = new Gson();
        String content = gson.toJson(requestDto);

        // when
        mockMvc.perform(
                        post("/api/v1/images")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.galContentId").exists())
                .andExpect(jsonPath("$.data.galTitle").exists())
                .andExpect(jsonPath("$.data.galWebImageUrl").exists())
                .andExpect(jsonPath("$.data.galPhotographer").exists())
                .andDo(print());

        // then
    }

    @Test
    void Image_CREATE_밸리데이션_테스트() throws Exception {
        // given
        given(imageService.create(any())).willReturn(responseDto);

        Gson gson = new Gson();
        String content = gson.toJson(invalidateRequestDto);

        // when
        mockMvc.perform(
                        post("/api/v1/images")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        // then
    }

    @Test
    void Image_UPDATE_contentId와_RequestDto를_받아_이미지_수정테스트() throws Exception {
        // given
        given(imageService.update(any(), any())).willReturn(responseDto);

        Gson gson = new Gson();
        String content = gson.toJson(requestDto);

        // when
        mockMvc.perform(
                        put("/api/v1/images/123")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.galContentId").exists())
                .andExpect(jsonPath("$.data.galTitle").exists())
                .andExpect(jsonPath("$.data.galWebImageUrl").exists())
                .andExpect(jsonPath("$.data.galPhotographer").exists())
                .andDo(print());

        // then
    }

    @Test
    void Image_DELETE_삭제_성공시_contentId_리턴() throws Exception {
        // given
        given(imageService.delete(123L)).willReturn(123L);

        // when
        mockMvc.perform(
                        delete("/api/v1/images/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());

        // then
        verify(imageService).delete(123L);
    }
}
