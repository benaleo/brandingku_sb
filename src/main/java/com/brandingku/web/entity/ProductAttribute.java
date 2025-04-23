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
@Table(name = "product_attributes", indexes = {
        @Index(name = "idx_product_attributes_secure_id", columnList = "secure_id", unique = true)
})
public class ProductAttribute extends AbstractEntity {

    private String category;

    private String name;

}
