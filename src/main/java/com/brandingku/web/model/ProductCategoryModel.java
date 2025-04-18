package com.brandingku.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ProductCategoryModel {

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ListProductCategoryResponse extends AdminModelBaseDTOResponse {
        private String name;
        private String slug;
        private String description;
        private String image;
    }

    public record DetailProductCategoryResponse(
            String name,
            String slug,
            String description
    ) {
    }

    public record CreateProductCategoryRequest(
            String name,
            String slug,
            String description
    ) {
    }

    public record UpdateProductCategoryRequest(
            String name,
            String slug,
            String description
    ) {
    }
}
