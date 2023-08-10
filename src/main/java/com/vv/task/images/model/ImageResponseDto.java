package com.vv.task.images.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
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

    private ImageResponseDto(Image image) {
        this.galContentId = image.getContentId();
        this.galContentTypeId = image.getContentTypeId();
        this.galTitle = image.getTitle();
        this.galWebImageUrl = image.getImageUrl();
        this.galCreatedtime = image.getImageUrl();
        this.galModifiedtime = image.getImageUrl();
        this.galPhotographyMonth = image.getPhotographyMonth();
        this.galPhotographyLocation = image.getPhotographyLocation();
        this.galPhotographer = image.getPhotographer();
        this.galSearchKeyword = image.getSearchKeyword();
    }

    public static ImageResponseDto from(Image image) {
        return new ImageResponseDto(image);
    }
}
