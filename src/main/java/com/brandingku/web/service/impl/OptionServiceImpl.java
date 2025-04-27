package com.brandingku.web.service.impl;

import com.brandingku.web.model.OptionResponse;
import com.brandingku.web.model.OptionWithCategoryResponse;
import com.brandingku.web.repository.ProductAttributeRepository;
import com.brandingku.web.repository.ProductCategoryRepository;
import com.brandingku.web.repository.ProductRepository;
import com.brandingku.web.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptionServiceImpl implements OptionService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductAttributeRepository productAttributeRepository;

    @Override
    public List<OptionResponse> getListProducts() {
        return productRepository.findAllByIsActiveIsTrue().stream()
                .map(product -> new OptionResponse(product.getSecureId(), product.getName()))
                .toList();
    }

    @Override
    public List<OptionResponse> getListProductCategories() {
        return productCategoryRepository.findAllByIsActiveIsTrueAndParentIsNotNull().stream()
                .map(productCategory -> new OptionResponse(productCategory.getSecureId(), productCategory.getName()))
                .toList();
    }

    @Override
    public List<OptionWithCategoryResponse> getListProductAttributes() {
        return productAttributeRepository.findAllByIsActiveIsTrueAndIsDeleteIsFalse().stream()
                .map(data -> new OptionWithCategoryResponse(data.getSecureId(), data.getName(), data.getCategory()))
                .toList();

    }
}
