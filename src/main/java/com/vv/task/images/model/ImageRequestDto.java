package com.vv.task.images.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@ToString
public class ImageRequestDto {

    private Long galContentId;

    private Long galContentTypeId;
    @NotBlank
    private String galTitle;
    @NotBlank
    private String galWebImageUrl;

    private String galCreatedtime;

    private String galModifiedtime;

    private String galPhotographyMonth;

    private String galPhotographyLocation;
    @NotBlank
    private String galPhotographer;

    private String galSearchKeyword;

    @Builder
    public ImageRequestDto(Long galContentId, String galTitle, String galPhotographer, String galWebImageUrl) {
        this.galContentId = galContentId;
        this.galTitle = galTitle;
        this.galPhotographer = galPhotographer;
        this.galWebImageUrl = galWebImageUrl;
    }
}
