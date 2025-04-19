package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ProductGalleryRepository extends JpaRepository<ProductGallery, Long> {
    Optional<ProductGallery> findBySecureId(String secureId);

    void deleteAllBySecureIdIn(Collection<String> secureIds);
}
