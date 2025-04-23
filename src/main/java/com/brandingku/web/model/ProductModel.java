package com.brandingku.web.model;

import com.brandingku.web.model.dto.ProductAdditionalDetailResponse;
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
            String highlight_description,
            String highlight_image,
            Boolean is_highlight,
            Boolean is_recommended,
            Boolean is_upsell,
            String category_name,
            String category_id,
            List<ProductAdditionalDetailResponse> additionals
    ) {
    }

    public record CreateProductRequest(
            String name,
            String slug,
            String description,
            Boolean is_highlight,
            String highlight_image,
            String highlight_description,
            Boolean is_recommended,
            Boolean is_upsell,
            String category_id,
            List<ProductAdditionalDetailResponse> additionals
    ) {
    }

    public record UpdateProductRequest(
            String name,
            String slug,
            String description,
            Boolean is_highlight,
            String highlight_image,
            String highlight_description,
            Boolean is_recommended,
            Boolean is_upsell,
            String category_id,
            List<ProductAdditionalDetailResponse> additionals
    ) {
    }
}
