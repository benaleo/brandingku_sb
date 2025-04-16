package com.brandingku.web.repository;

import com.brandingku.web.entity.Product;
import com.brandingku.web.model.projection.ProductIndexProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT new com.brandingku.web.model.projection.ProductIndexProjection(
                p.name, p.slug, p.description, p.price, p.discount, p.discountType, p.quantity, p.thumbnail, p.isRecommended, p.isUpsell, pc.name,
                p.id, p.secureId, p.createdAt, uc.name, p.updatedAt, uu.name
            )
            FROM Product p
            LEFT JOIN Users uc ON uc.id = p.createdBy
            LEFT JOIN Users uu ON uu.id = p.updatedBy
            LEFT JOIN ProductCategory pc ON pc.id = p.category.id
            WHERE p.isDelete = false AND
            (LOWER(p.name) LIKE LOWER(:keyword))
            """)
    Page<ProductIndexProjection> findDataByKeyword(String keyword, Pageable pageable);

    Optional<Product> findBySecureId(String id);

    @Modifying
    @Transactional
    @Query("UPDATE Product d SET d.isDelete = true WHERE d = :data")
    void softDelete(Product data);
}
