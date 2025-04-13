package com.brandingku.web.entity;

import jakarta.persistence.*;
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
@Table(name = "products", indexes = {
        @Index(name = "idx_products_secure_id", columnList = "secure_id", unique = true)
})
public class Products extends AbstractEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug = UUID.randomUUID().toString();
    
    @Column(columnDefinition = "text")
    private String description;

    private Integer price;

    private Integer discount;

    private Integer discountType;

    private Integer quantity;

    @Column(columnDefinition = "text")
    private String thumbnail;

    private Boolean isRecommended = false;

    private Boolean isUpsell = false;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "secure_id")
    private ProductCategories category;

    @Override
    public Long getId() {return super.getId();}

    @Override
    public String getSecureId() {return super.getSecureId();}

    @Override
    public Boolean getIsActive() {return super.getIsActive();}
}
