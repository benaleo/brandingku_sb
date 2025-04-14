package com.brandingku.web.service.impl;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductCategoryModel;
import com.brandingku.web.repository.ProductCategoryRepository;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ResultPageResponseDTO<ProductCategoryModel.ListProductCategoryResponse> getAllProductCategory(CompilerPagination f) {
        return null;
    }

    @Override
    public ProductCategoryModel.DetailProductCategoryResponse getDetailProductCategory(String id) {
        return null;
    }

    @Override
    public void createProductCategory(ProductCategoryModel.@Valid CreateProductCategoryRequest req) {

    }

    @Override
    public void updateProductCategory(String id, ProductCategoryModel.UpdateProductCategoryRequest req) {

    }

    @Override
    public void deleteProductCategory(String id) {

    }
}
