package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductCategory;
import com.brandingku.web.model.OptionResponse;
import com.brandingku.web.model.projection.CastIdSecureIdProjection;
import com.brandingku.web.model.projection.ProductCategoryIndexProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("""
            SELECT new com.brandingku.web.model.projection.ProductCategoryIndexProjection(
                pc.name, pc.slug, pc.description, pc.image, pc.isLandingPage, pc.isActive,
                pc.id, pc.secureId, pc.createdAt, uc.name, pc.updatedAt, uu.name
            )
            FROM ProductCategory pc
            LEFT JOIN Users uc ON uc.id = pc.createdBy
            LEFT JOIN Users uu ON uu.id = pc.updatedBy
            WHERE pc.isDelete = false AND pc.parent IS NULL AND
            (LOWER(pc.name) LIKE LOWER(:keyword))
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

    @Query("""
            SELECT pc
            FROM ProductCategory pc
            WHERE
                (:slug IS NULL OR pc.slug = :slug) AND
                pc.isLandingPage = true AND
                pc.isDelete = false AND
                pc.isActive = true
            """)
    Page<ProductCategory> findAllOrBySlugInLanding(Pageable pageable, String slug);

    List<ProductCategory> findAllByIsActiveIsTrueAndParentIsNotNull();

    List<ProductCategory> findAllByParent(ProductCategory parent);

    @Modifying
    @Transactional
    @Query("UPDATE ProductCategory d SET d.isActive = :isActive WHERE d.secureId IN :filterSubCategories")
    void updateIsActive(boolean isActive, List<String> filterSubCategories);

    boolean existsByNameAndParentAndIsActiveIsFalse(String name, ProductCategory parent);

}
