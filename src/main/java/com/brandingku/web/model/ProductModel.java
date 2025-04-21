package com.brandingku.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class ProductModel {

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ListProductResponse extends AdminModelBaseDTOResponse {
        private String name;
        private String slug;
        private String description;
        private String highlight_description;
        private Integer price;
        private Integer discount;
        private String discount_type;
        private Integer quantity;
        private String image;
        private List<ProductGalleryOptions> galleries;
        private String highlight_image;
        private Boolean is_highlight;
        private Boolean is_recommended;
        private Boolean is_upsell;
        private String category_name;

        @Data
        @AllArgsConstructor
        public static class ProductGalleryOptions {
            private String id;
            private String url;
        }
    }

    public record DetailProductResponse(
            String name,
            String slug,
            String description,
            Integer price,
            Integer discount,
            String discount_type,
            Integer quantity,
            Boolean is_recommended,
            Boolean is_upsell,
            String category_name,
            String category_id
    ) {
    }

    public record CreateProductRequest(
            String name,
            String slug,
            String description,
            Integer price,
            Integer discount,
            String discount_type,
            Integer quantity,
            Boolean is_recommended,
            Boolean is_upsell,
            String category_id
    ) {
    }

    public record UpdateProductRequest(
            String name,
            String slug,
            String description,
            Integer price,
            Integer discount,
            String discount_type,
            Integer quantity,
            Boolean is_recommended,
            Boolean is_upsell,
            String category_id
    ) {
    }
}
