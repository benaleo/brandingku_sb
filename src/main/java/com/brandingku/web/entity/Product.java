package com.brandingku.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", indexes = {
        @Index(name = "idx_products_secure_id", columnList = "secure_id", unique = true)
})
public class Product extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug = UUID.randomUUID().toString();

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String HighlightDescription;

    private Integer price;

    private Integer discount;

    private String discountType;

    private Integer quantity;

    @Column(columnDefinition = "text")
    private String highlightImage;

    private Boolean isHighlight = false;

    private Boolean isRecommended = false;

    private Boolean isUpsell = false;

    @OneToMany(mappedBy = "product",  fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductGallery> listGallery;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "secure_id")
    private ProductCategory category;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getSecureId() {
        return super.getSecureId();
    }

    @Override
    public Boolean getIsActive() {
        return super.getIsActive();
    }

    public String getFirstImage() {
        if (listGallery != null && listGallery.size() > 0) {
            return listGallery.get(0).getUrlFile();
        }
        return null;
    }
}
