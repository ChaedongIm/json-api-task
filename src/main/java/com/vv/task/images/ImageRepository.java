package com.vv.task.images;

import com.vv.task.images.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select i from images i where i.contentId = :contentId")
    Optional<Image> findByContentId(Long contentId);
}
