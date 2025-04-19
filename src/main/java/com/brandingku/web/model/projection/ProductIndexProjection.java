package com.brandingku.web.model.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductIndexProjection {
    private String name;
    private String slug;
    private String description;
    private String highlightDescription;
    private Integer price;
    private Integer discount;
    private String discountType;
    private Integer quantity;
    private Boolean isHighlight;
    private Boolean isRecommended;
    private Boolean isUpsell;
    private String categoryName;

    private Long id;
    private String secureId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
