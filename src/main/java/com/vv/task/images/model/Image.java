package com.vv.task.images.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long contentId;

    private Long contentTypeId;

    private String title;

    private String imageUrl;

    private String createdTime;

    private String modifiedTime;

    private String photographyMonth;

    private String photographyLocation;

    private String photographer;

    private String searchKeyword;

    private Image(ImageResponseDto dto) {
        this.contentId = dto.getGalContentId();
        this.contentTypeId = dto.getGalContentId();
        this.title = dto.getGalTitle();
        this.imageUrl = dto.getGalWebImageUrl();
        this.createdTime = dto.getGalCreatedtime();
        this.modifiedTime = dto.getGalCreatedtime();
        this.photographyMonth = dto.getGalPhotographyMonth();
        this.photographyLocation = dto.getGalPhotographyLocation();
        this.photographer = dto.getGalPhotographer();
        this.searchKeyword = dto.getGalSearchKeyword();
    }

    public static Image from(ImageResponseDto dto) {
        return new Image(dto);
    }

    private Image(ImageRequestDto dto) {
        this.contentId = dto.getGalContentId();
        this.contentTypeId = dto.getGalContentId();
        this.title = dto.getGalTitle();
        this.imageUrl = dto.getGalWebImageUrl();
        this.createdTime = dto.getGalCreatedtime();
        this.modifiedTime = dto.getGalCreatedtime();
        this.photographyMonth = dto.getGalPhotographyMonth();
        this.photographyLocation = dto.getGalPhotographyLocation();
        this.photographer = dto.getGalPhotographer();
        this.searchKeyword = dto.getGalSearchKeyword();
    }

    public static Image from(ImageRequestDto dto) {
        return new Image(dto);
    }

    public Image update(ImageRequestDto dto) {
        this.contentTypeId = dto.getGalContentId();
        this.title = dto.getGalTitle();
        this.imageUrl = dto.getGalWebImageUrl();
        this.modifiedTime = dto.getGalModifiedtime();
        this.photographyMonth = dto.getGalPhotographyMonth();
        this.photographyLocation = dto.getGalPhotographyLocation();
        this.photographer = dto.getGalPhotographer();
        this.searchKeyword = dto.getGalSearchKeyword();

        return this;
    }
}


/**
 * "galContentId": "2859292",
 * "galContentTypeId": "17",
 * "galTitle": "1100고지습지",
 * "galWebImageUrl": "http://tong.visitkorea.or.kr/cms2/website/92/2859292.jpg",
 * "galCreatedtime": "20220926105242",
 * "galModifiedtime": "20220926105253",
 * "galPhotographyMonth": "202207",
 * "galPhotographyLocation": "제주특별자치도 서귀포시",
 * "galPhotographer": "한국관광공사 이범수",
 * "galSearchKeyword": "1100고지습지, 제주특별자치도 서귀포시, 제주도 오름, 제주오름, 1100고지 탐방로"
 */
