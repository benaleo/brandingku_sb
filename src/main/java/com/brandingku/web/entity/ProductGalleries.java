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
@Table(name = "product_galleries", indexes = {
        @Index(name = "idx_product_galleries_secure_id", columnList = "secure_id", unique = true)
})
public class ProductGalleries extends AbstractEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String urlFile;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "secure_id")
    private Products product;

    @Override
    public Long getId() {return super.getId();}

    @Override
    public String getSecureId() {return super.getSecureId();}

    @Override
    public Boolean getIsActive() {return super.getIsActive();}
}
