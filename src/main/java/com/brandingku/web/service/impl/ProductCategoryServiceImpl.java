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
import com.brandingku.web.util.ContextPrincipal;
import com.brandingku.web.util.GlobalConverter;
import com.brandingku.web.util.TreeGetEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;

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
        ProductCategory data = TreeGetEntity.parsingProductCategoryByProjection(id, productCategoryRepository);
        return new ProductCategoryModel.DetailProductCategoryResponse(
                data.getName(),
                data.getSlug(),
                data.getDescription()
        );
    }

    @Override
    public void createProductCategory(ProductCategoryModel.@Valid CreateProductCategoryRequest req) {
        Users user = TreeGetEntity.parsingUserByProjection(ContextPrincipal.getSecureUserId(), userRepository);

        long countAllData = productCategoryRepository.count();

        ProductCategory data = new ProductCategory();
        data.setName(req.name());
        data.setSlug(req.name() + "-" + (countAllData + 1L));
        data.setDescription(req.description());
        GlobalConverter.CmsAdminCreateAtBy(data, user.getId());
        productCategoryRepository.save(data);
    }

    @Override
    public void updateProductCategory(String id, ProductCategoryModel.UpdateProductCategoryRequest req) {
        Users user = TreeGetEntity.parsingUserByProjection(ContextPrincipal.getSecureUserId(), userRepository);

        ProductCategory data = TreeGetEntity.parsingProductCategoryByProjection(id, productCategoryRepository);
        data.setName(req.name() != null ? req.name() : data.getName());
        data.setDescription(req.description() != null ? req.description() : data.getDescription());
        GlobalConverter.CmsAdminUpdateAtBy(data, user.getId());
        productCategoryRepository.save(data);
    }

    @Override
    public void deleteProductCategory(String id) {
        ProductCategory data = TreeGetEntity.parsingProductCategoryByProjection(id, productCategoryRepository);
        productCategoryRepository.softDelete(data);
    }
}
