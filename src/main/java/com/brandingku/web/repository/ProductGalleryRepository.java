package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductGalleryRepository extends JpaRepository<ProductGallery, Long> {
    Optional<ProductGallery> findBySecureId(String secureId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductGallery d WHERE d.secureId IN :secureIds")
    void deleteAllBySecureIdIn(List<String> secureIds);
}
