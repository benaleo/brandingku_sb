package com.brandingku.web.service;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductModel;
import com.brandingku.web.response.ResultPageResponseDTO;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ResultPageResponseDTO<ProductModel.ListProductResponse> getAllProduct(CompilerPagination f);

    ProductModel.DetailProductResponse getDetailProduct(String id);

    void createProduct(ProductModel.@Valid CreateProductRequest req);

    void updateProduct(String id, ProductModel.@Valid UpdateProductRequest req);

    void deleteProduct(String id);

    ProductModel.ListProductResponse postHighlightProduct(String id, String file, String description, Boolean isHighlight) throws IOException;

    ProductModel.ListProductResponse postGalleryProduct(String id, List<String> newFile, List<String> removeId) throws IOException;
}
