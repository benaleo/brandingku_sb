package com.brandingku.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_galleries", indexes = {
        @Index(name = "idx_product_galleries_secure_id", columnList = "secure_id", unique = true)
})
public class ProductGallery extends AbstractEntity{

    @Column(columnDefinition = "text")
    private String urlFile;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "secure_id")
    private Product product;

    @Column(name = "is_thumbnail")
    private Boolean isThumbnail = false;

    @Override
    public Long getId() {return super.getId();}

    @Override
    public String getSecureId() {return super.getSecureId();}

    @Override
    public Boolean getIsActive() {return super.getIsActive();}
}
