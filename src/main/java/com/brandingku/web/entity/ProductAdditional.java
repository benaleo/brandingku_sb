package com.brandingku.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_additionals", indexes = {
        @Index(name = "idx_product_additional_secure_id", columnList = "secure_id", unique = true)
})
public class ProductAdditional  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "secure_id", nullable = false)
    private String secureId = UUID.randomUUID().toString();

    @Column(name = "price", nullable = false)
    private Integer price;

    private Integer moq;

    private Integer stock;

    private Integer discount;

    private String discountType;

    @OneToMany(mappedBy = "additional", cascade = CascadeType.ALL)
    private List<ProductAdditionalHasAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "secure_id")
    private Product product;

    public ProductAdditional(Product product, Integer price, Integer moq, Integer stock, Integer discount, String discountType) {
        this.price = price;
        this.moq = moq;
        this.stock = stock;
        this.discount = discount;
        this.discountType = discountType;
        this.product = product;
    }
}
