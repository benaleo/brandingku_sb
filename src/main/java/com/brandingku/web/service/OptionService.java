package com.brandingku.web.service;

import com.brandingku.web.model.OptionResponse;
import com.brandingku.web.model.OptionWithCategoryResponse;

import java.util.List;

public interface OptionService {
    List<OptionResponse> getListProducts();

    List<OptionResponse> getListProductCategories();

    List<OptionWithCategoryResponse> getListProductAttributes();
}
