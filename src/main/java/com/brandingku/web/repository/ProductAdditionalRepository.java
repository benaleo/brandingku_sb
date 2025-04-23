package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductAdditional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductAdditionalRepository extends JpaRepository<ProductAdditional, Long> {
    Optional<ProductAdditional> findBySecureId(String secureId);
}
