package com.vv.task.images.model;

import lombok.Builder;
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


    @Builder
    public ImageResponseDto(Long galContentId, String galTitle, String galPhotographer, String galWebImageUrl) {
        this.galContentId = galContentId;
        this.galTitle = galTitle;
        this.galPhotographer = galPhotographer;
        this.galWebImageUrl = galWebImageUrl;
    }

    private ImageResponseDto(Image image) {
        this.galContentId = image.getContentId();
        this.galContentTypeId = image.getContentTypeId();
        this.galTitle = image.getTitle();
        this.galWebImageUrl = image.getImageUrl();
        this.galCreatedtime = image.getCreatedTime();
        this.galModifiedtime = image.getModifiedTime();
        this.galPhotographyMonth = image.getPhotographyMonth();
        this.galPhotographyLocation = image.getPhotographyLocation();
        this.galPhotographer = image.getPhotographer();
        this.galSearchKeyword = image.getSearchKeyword();
    }

    public static ImageResponseDto from(Image image) {
        return new ImageResponseDto(image);
    }
}
