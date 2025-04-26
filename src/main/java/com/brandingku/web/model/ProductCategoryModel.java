package com.brandingku.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class ProductCategoryModel {

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ListProductCategoryResponse extends AdminModelBaseDTOResponse {
        private String name;
        private String slug;
        private String description;
        private String image;
        private List<String> sub_categories;
        private Boolean is_landing_page;
        private Boolean is_active;
    }

    public record DetailProductCategoryResponse(
            String name,
            String slug,
            String description,
            List<String> sub_categories,
            Boolean is_landing_page,
            Boolean is_active
    ) {
    }

    public record CreateProductCategoryRequest(
            String name,
            String slug,
            String description,
            List<String> sub_categories,
            Boolean is_landing_page,
            Boolean is_active
    ) {
    }

    public record UpdateProductCategoryRequest(
            String name,
            String slug,
            String description,
            List<String> sub_categories,
            Boolean is_landing_page,
            Boolean is_active
    ) {
    }
}
