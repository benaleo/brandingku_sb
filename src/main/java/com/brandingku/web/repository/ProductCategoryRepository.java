package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductCategory;
import com.brandingku.web.model.projection.ProductCategoryIndexProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("""
            SELECT new com.brandingku.web.model.projection.ProductCategoryIndexProjection(
                pc.name, pc.slug, pc.description,
                pc.id, pc.secureId, pc.createdAt, uc.name, pc.updatedAt, uu.name
            )
            FROM ProductCategory pc
            LEFT JOIN Users uc ON uc.id = pc.createdBy
            LEFT JOIN Users uu ON uu.id = pc.updatedBy
            WHERE pc.isDelete = false
            """)
    Page<ProductCategoryIndexProjection> findDataByKeyword(String keyword, Pageable pageable);
}
