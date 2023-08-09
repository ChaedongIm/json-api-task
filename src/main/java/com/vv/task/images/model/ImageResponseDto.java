package com.vv.task.images.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ImageResponseDto {
    private Long galContentId;

    private Long galContentTypeId;

    private String galTitle;

    private String galWebImageUrl;

    private String galCreatedtime;

    private String galModifiedtime;

    private String galPhotographyMonth;

    private String galPhotographyLocation;

    private String galPhotographer;

    private String galSearchKeyword;
}
