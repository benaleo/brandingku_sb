package com.brandingku.web.service.impl;

import com.brandingku.web.entity.ProductCategory;
import com.brandingku.web.entity.Users;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductCategoryModel;
import com.brandingku.web.model.projection.ProductCategoryIndexProjection;
import com.brandingku.web.model.search.ListOfFilterPagination;
import com.brandingku.web.model.search.SavedKeywordAndPageable;
import com.brandingku.web.repository.ProductCategoryRepository;
import com.brandingku.web.repository.UserRepository;
import com.brandingku.web.response.PageCreateReturn;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.ProductCategoryService;
import com.brandingku.web.service.util.UrlConverterService;
import com.brandingku.web.util.ContextPrincipal;
import com.brandingku.web.util.GlobalConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;
    private final UrlConverterService urlConverterService;

    @Override
    public ResultPageResponseDTO<ProductCategoryModel.ListProductCategoryResponse> getAllProductCategory(CompilerPagination f) {
        ListOfFilterPagination filter = new ListOfFilterPagination(f.keyword());
        SavedKeywordAndPageable set = GlobalConverter.appsCreatePageable(f.pages(), f.limit(), f.sortBy(), f.direction(), f.keyword(), filter);

        // First page result (get total count)
        Page<ProductCategoryIndexProjection> firstResult = productCategoryRepository.findDataByKeyword(set.keyword(), set.pageable());

        // Use a correct Pageable for fetching the next page
        Pageable pageable = GlobalConverter.oldSetPageable(f.pages(), f.limit(), f.sortBy(), f.direction(), firstResult, null);
        Page<ProductCategoryIndexProjection> pageResult = productCategoryRepository.findDataByKeyword(set.keyword(), pageable);

        // Map the data to the DTOs
        List<ProductCategoryModel.ListProductCategoryResponse> dtos = pageResult.stream().map((c) -> {
            ProductCategoryModel.ListProductCategoryResponse dto = new ProductCategoryModel.ListProductCategoryResponse();
            dto.setName(c.getName());
            dto.setSlug(c.getSlug());
            dto.setDescription(c.getDescription());
            dto.setImage(c.getImage());

            GlobalConverter.CmsIDTimeStampResponseAndIdProjection(dto, c.getSecureId(), c.getCreatedAt(), c.getUpdatedAt(), c.getCreatedBy(), c.getCreatedBy());
            return dto;
        }).collect(Collectors.toList());

        return PageCreateReturn.create(
                pageResult,
                dtos
        );
    }

    @Override
    public ProductCategoryModel.DetailProductCategoryResponse getDetailProductCategory(String id) {
        ProductCategory data = productCategoryRepository.findBySecureId(id).orElse(null);
        return new ProductCategoryModel.DetailProductCategoryResponse(
                data == null ? null : data.getName(),
                data == null ? null : data.getSlug(),
                data == null ? null : data.getDescription()
        );
    }

    @Override
    public void createProductCategory(ProductCategoryModel.@Valid CreateProductCategoryRequest req) {
        Users user = userRepository.findById(ContextPrincipal.getId()).orElse(null);

        long countAllData = productCategoryRepository.count();

        ProductCategory data = new ProductCategory();
        data.setName(req.name());
        data.setSlug(req.slug());
        data.setDescription(req.description());
        GlobalConverter.CmsAdminCreateAtBy(data, user != null ? user.getId() : null);
        productCategoryRepository.save(data);
    }

    @Override
    public void updateProductCategory(String id, ProductCategoryModel.UpdateProductCategoryRequest req) {
        Users user = userRepository.findById(ContextPrincipal.getId()).orElse(null);

        ProductCategory data = productCategoryRepository.findBySecureId(id).orElse(null);
        if (data != null) {
            data.setName(req.name() != null ? req.name() : data.getName());
            data.setSlug(req.slug() != null ? req.slug() : data.getSlug());
            data.setDescription(req.description() != null ? req.description() : data.getDescription());
            GlobalConverter.CmsAdminUpdateAtBy(data, user != null ? user.getId() : null);
            productCategoryRepository.save(data);
        }
    }

    @Override
    public void deleteProductCategory(String id) {
        Users user = userRepository.findById(ContextPrincipal.getId()).orElse(null);
        ProductCategory data = productCategoryRepository.findBySecureId(id).orElse(null);
        productCategoryRepository.softDelete(data, user != null ? user.getId() : null);
    }

    @Override
    public void postImageProductCategory(String id, MultipartFile file) throws IOException {
        ProductCategory data = productCategoryRepository.findBySecureId(id).orElse(null);
        if (data != null) {
            data.setImage(urlConverterService.saveUrlImageProductCategory(file));
            productCategoryRepository.save(data);
        }
    }
}
