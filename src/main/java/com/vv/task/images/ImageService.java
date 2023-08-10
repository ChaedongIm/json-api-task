package com.vv.task.images;

import com.vv.task.images.model.Image;
import com.vv.task.images.model.ImageRequestDto;
import com.vv.task.images.model.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public Page<ImageResponseDto> findAll(Pageable pageable) {
        return imageRepository.findAll(pageable)
                .map(image -> ImageResponseDto.from(image));
    }

    @Transactional(readOnly = true)
    public ImageResponseDto findByGalContentId(Long galContentId) {
        return ImageResponseDto.from(
                imageRepository.findByContentId(galContentId)
                        .orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public ImageResponseDto create(ImageRequestDto requestDto) {
        if (imageRepository.findByContentId(requestDto.getGalContentId()).isPresent()) {
            throw new EntityExistsException();
        }

        Image image = imageRepository.save(Image.from(requestDto));
        return ImageResponseDto.from(image);
    }

    @Transactional
    public ImageResponseDto update(Long contentId, ImageRequestDto requestDto) {
        Image image = imageRepository.findByContentId(contentId)
                .orElseThrow(EntityNotFoundException::new);

        return ImageResponseDto.from(image.update(requestDto));
    }

    @Transactional
    public void delete() {
    }
}
