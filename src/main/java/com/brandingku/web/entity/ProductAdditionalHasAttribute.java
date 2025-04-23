package com.brandingku.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_attributes", indexes = {
        @Index(name = "idx_product_attributes_secure_id", columnList = "secure_id", unique = true)
})
public class ProductAdditionalHasAttribute  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime ts;

    @ManyToOne
    @JoinColumn(name = "additional_id", referencedColumnName = "secure_id")
    private ProductAdditional additional;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "secure_id")
    private ProductAttribute attribute;

    @PrePersist
    protected void onCreate() {
        ts = LocalDateTime.now();
    }

    public ProductAdditionalHasAttribute(ProductAdditional additional, ProductAttribute attribute) {
        this.additional = additional;
        this.attribute = attribute;
    }
}
