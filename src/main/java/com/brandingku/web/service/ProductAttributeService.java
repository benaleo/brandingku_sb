package com.brandingku.web.service;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductAttributeModel;
import com.brandingku.web.response.ResultPageResponseDTO;
import jakarta.validation.Valid;

public interface ProductAttributeService {
    ResultPageResponseDTO<ProductAttributeModel.ListProductAttributeResponse> getAllProductAttribute(CompilerPagination f);

    ProductAttributeModel.DetailProductAttributeResponse getDetailProductAttribute(String id);

    void createProductAttribute(ProductAttributeModel.@Valid CreateProductAttributeRequest req);

    void updateProductAttribute(String id, ProductAttributeModel.@Valid UpdateProductAttributeRequest req);

    void deleteProductAttribute(String id);
}
