package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductAttribute;
import com.brandingku.web.model.projection.CastIdSecureIdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    List<ProductAttribute> findAllBySecureIdIn(List<String> attributeIds);

    @Query("""
            SELECT pa FROM ProductAttribute pa
            WHERE
            (LOWER(pa.name) LIKE LOWER(:keyword) OR
            LOWER(pa.category) LIKE LOWER(:keyword)) AND
            pa.isDelete = false AND
            (:category IS NULL OR pa.category = :category)
            """)
    Page<ProductAttribute> findAllByKeywords(String keyword, String category, Pageable pageable);

    @Query("""
            SELECT new com.brandingku.web.model.projection.CastIdSecureIdProjection(d.id, d.secureId)
            FROM ProductAttribute d
            WHERE d.secureId = :secureId
            """)
    Optional<CastIdSecureIdProjection> findIdBySecureId(String secureId);

    @Modifying
    @Transactional
    @Query("UPDATE ProductAttribute d SET d.isDelete = true WHERE d = :data")
    void updateSoftDelete(ProductAttribute data);

    List<ProductAttribute> findAllByIsActiveIsTrueAndIsDeleteIsFalse();
}
