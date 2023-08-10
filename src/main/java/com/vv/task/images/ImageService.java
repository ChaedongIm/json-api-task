package com.vv.task.images;

import com.vv.task.images.model.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ImageResponseDto findByImageId() {
        return null;
    }

    @Transactional
    public ImageResponseDto create() {
        return null;
    }

    @Transactional
    public ImageResponseDto update() {
        return null;
    }

    @Transactional
    public void delete() {
    }
}
