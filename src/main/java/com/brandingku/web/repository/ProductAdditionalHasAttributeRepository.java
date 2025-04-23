package com.brandingku.web.repository;

import com.brandingku.web.entity.ProductAdditional;
import com.brandingku.web.entity.ProductAdditionalHasAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductAdditionalHasAttributeRepository extends JpaRepository<ProductAdditionalHasAttribute, Long> {

    List<ProductAdditionalHasAttribute> findAllByAdditional(ProductAdditional additional);

    void deleteAllByAdditional(ProductAdditional additional);

    void deleteAllByAdditionalIn(Collection<ProductAdditional> additionals);
}
