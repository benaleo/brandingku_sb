package com.brandingku.web.model.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductCategoryIndexProjection {
    private String name;
    private String slug;
    private String description;

    private Long id;
    private String secureId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
