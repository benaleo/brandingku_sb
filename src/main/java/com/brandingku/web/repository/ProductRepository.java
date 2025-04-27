package com.brandingku.web.repository;

import com.brandingku.web.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            JOIN p.category pc
            WHERE p.isDelete = false AND
            (LOWER(p.name) LIKE LOWER(:keyword) OR
            LOWER(pc.name) LIKE LOWER(:keyword)) AND
            (:category IS NULL OR pc.name = :category)
            """)
    Page<Product> findDataByKeyword(String keyword, String category, Pageable pageable);

    Optional<Product> findBySecureId(String id);

    @Modifying
    @Transactional
    @Query("UPDATE Product d SET d.isDelete = true WHERE d = :data")
    void softDelete(Product data);

    List<Product> findAllByIsActiveIsTrue();
}
