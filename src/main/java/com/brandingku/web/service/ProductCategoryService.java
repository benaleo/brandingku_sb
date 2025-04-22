package com.brandingku.web.service;

import com.brandingku.web.model.AppLandingFeaturedCategoryResponse;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductCategoryModel;
import com.brandingku.web.response.ResultPageResponseDTO;
import jakarta.validation.Valid;

import java.io.IOException;

public interface ProductCategoryService {
    ResultPageResponseDTO<ProductCategoryModel.ListProductCategoryResponse> getAllProductCategory(CompilerPagination f);

    ProductCategoryModel.DetailProductCategoryResponse getDetailProductCategory(String id);

    void createProductCategory(ProductCategoryModel.@Valid CreateProductCategoryRequest req);

    void updateProductCategory(String id, ProductCategoryModel.UpdateProductCategoryRequest req);

    void deleteProductCategory(String id);

    void postImageProductCategory(String id, String file) throws IOException;

    // App
    ResultPageResponseDTO<AppLandingFeaturedCategoryResponse> getFeaturedCategory(CompilerPagination f, String slug);
}
