package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductAdditional;
import com.brandingku.web.entity.ProductAdditionalHasAttribute;
import com.brandingku.web.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface ProductAdditionalHasAttributeRepository extends JpaRepository<ProductAdditionalHasAttribute, Long> {

    List<ProductAdditionalHasAttribute> findAllByAdditional(ProductAdditional additional);

    void deleteAllByAdditional(ProductAdditional additional);

    @Modifying
    @Transactional
    void deleteAllByAdditionalIn(Collection<ProductAdditional> additionals);

    boolean existsByAdditionalAndAttribute(ProductAdditional additional, ProductAttribute attribute);
}
