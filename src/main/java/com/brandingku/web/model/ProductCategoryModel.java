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
    }

    @Data
    @AllArgsConstructor
    public static class DetailProductCategoryResponse {
    }

    @Data
    public static class CreateProductCategoryRequest {
    }

    @Data
    public static class UpdateProductCategoryRequest {
    }
}
