package com.brandingku.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_categories", indexes = {
        @Index(name = "idx_product_categories_secure_id", columnList = "secure_id", unique = true)
})
public class ProductCategories extends AbstractEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug = UUID.randomUUID().toString();

    @Column(columnDefinition = "text")
    private String description;

    @Override
    public Long getId() {return super.getId();}

    @Override
    public String getSecureId() {return super.getSecureId();}

    @Override
    public Boolean getIsActive() {return super.getIsActive();}
}
