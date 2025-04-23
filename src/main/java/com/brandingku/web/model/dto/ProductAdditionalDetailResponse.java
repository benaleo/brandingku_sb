package com.brandingku.web.model.dto;

import java.util.List;

public record ProductAdditionalDetailResponse(
        String id,
        int price,
        int moq,
        int stock,
        int discount,
        String discount_type,
        List<ProductAttributeDetailResponse> attributes
) {
}
