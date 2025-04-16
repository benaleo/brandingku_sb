package com.brandingku.web.service;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductModel;
import com.brandingku.web.response.ResultPageResponseDTO;
import jakarta.validation.Valid;

public interface ProductService {
    ResultPageResponseDTO<ProductModel.ListProductResponse> getAllProduct(CompilerPagination f);

    ProductModel.DetailProductResponse getDetailProduct(String id);

    void createProduct(ProductModel.@Valid CreateProductRequest req);

    void updateProduct(String id, ProductModel.@Valid UpdateProductRequest req);

    void deleteProduct(String id);
}
