package com.brandingku.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ProductAttributeModel {

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ListProductAttributeResponse extends AdminModelBaseDTOResponse {
        private String name;
        private String category;
        private Boolean is_active;
    }


    public record DetailProductAttributeResponse(
            String name,
            String category,
            Boolean is_active
    ) {
    }

    public record CreateProductAttributeRequest(
            String name,
            String category,
            Boolean is_active
    ) {
    }

    public record UpdateProductAttributeRequest(
            String name,
            String category,
            Boolean is_active
    ) {
    }
}
