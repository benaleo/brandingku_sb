package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    List<ProductAttribute> findAllBySecureIdIn(List<String> attributeIds);
}
