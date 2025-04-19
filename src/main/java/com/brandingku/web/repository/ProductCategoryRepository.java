package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductCategory;
import com.brandingku.web.model.projection.CastIdSecureIdProjection;
import com.brandingku.web.model.projection.ProductCategoryIndexProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("""
            SELECT new com.brandingku.web.model.projection.ProductCategoryIndexProjection(
                pc.name, pc.slug, pc.description, pc.image,
                pc.id, pc.secureId, pc.createdAt, uc.name, pc.updatedAt, uu.name
            )
            FROM ProductCategory pc
            LEFT JOIN Users uc ON uc.id = pc.createdBy
            LEFT JOIN Users uu ON uu.id = pc.updatedBy
            WHERE pc.isDelete = false
            """)
    Page<ProductCategoryIndexProjection> findDataByKeyword(String keyword, Pageable pageable);


    @Query("""
            SELECT new com.brandingku.web.model.projection.CastIdSecureIdProjection(d.id, d.secureId)
            FROM ProductCategory d
            WHERE d.secureId = :secureId
            """)
    Optional<CastIdSecureIdProjection> findIdBySecureId(String s);

    @Modifying
    @Transactional
    @Query("""
            UPDATE ProductCategory d 
            SET d.isDelete = true,
                d.updatedAt = CURRENT_TIMESTAMP,
                d.updatedBy = :id,
                d.slug = d.slug || '-' || CURRENT_TIMESTAMP
            WHERE d = :data
            """)
    void softDelete(ProductCategory data, Long id);

    Optional<ProductCategory> findBySecureId(String s);

    Page<ProductCategory> findAllByIsActiveIsTrueAndIsLandingPageIsTrueAndParentIdIsNull(Pageable pageable);
}
