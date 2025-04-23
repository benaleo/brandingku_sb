package com.brandingku.web.service.impl;

import com.brandingku.web.entity.ProductAttribute;
import com.brandingku.web.entity.Users;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductAttributeModel;
import com.brandingku.web.model.search.ListOfFilterPagination;
import com.brandingku.web.model.search.SavedKeywordAndPageable;
import com.brandingku.web.repository.ProductAttributeRepository;
import com.brandingku.web.repository.UserRepository;
import com.brandingku.web.response.PageCreateReturn;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.ProductAttributeService;
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
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;
    private final UserRepository userRepository;

    @Override
    public ResultPageResponseDTO<ProductAttributeModel.ListProductAttributeResponse> getAllProductAttribute(CompilerPagination f) {
        ListOfFilterPagination filter = new ListOfFilterPagination(f.keyword());
        SavedKeywordAndPageable set = GlobalConverter.appsCreatePageable(f.pages(), f.limit(), f.sortBy(), f.direction(), f.keyword(), filter);

        // First page result (get total count)
        Page<ProductAttribute> firstResult = productAttributeRepository.findAllByKeywords(set.keyword(), set.pageable());

        // Use a correct Pageable for fetching the next page
        Pageable pageable = GlobalConverter.oldSetPageable(f.pages(), f.limit(), f.sortBy(), f.direction(), firstResult, null);
        Page<ProductAttribute> pageResult = productAttributeRepository.findAllByKeywords(set.keyword(), pageable);

        // Map the data to the DTOs
        List<ProductAttributeModel.ListProductAttributeResponse> dtos = pageResult.stream().map((c) -> {
            ProductAttributeModel.ListProductAttributeResponse dto = new ProductAttributeModel.ListProductAttributeResponse();
            dto.setName(c.getName());
            dto.setCategory(c.getCategory());
            dto.setIs_active(c.getIsActive());

            GlobalConverter.CmsIDTimeStampResponseAndId(dto, c, userRepository);
            return dto;
        }).collect(Collectors.toList());

        return PageCreateReturn.create(
                pageResult,
                dtos
        );
    }

    @Override
    public ProductAttributeModel.DetailProductAttributeResponse getDetailProductAttribute(String id) {
        ProductAttribute data = TreeGetEntity.parsingProductAttributeByProjection(id, productAttributeRepository);
        return new ProductAttributeModel.DetailProductAttributeResponse(
                data.getName(),
                data.getCategory(),
                data.getIsActive()
        );
    }

    @Override
    public void createProductAttribute(ProductAttributeModel.@Valid CreateProductAttributeRequest req) {
        Users user = TreeGetEntity.parsingUserByProjection(ContextPrincipal.getSecureUserId(), userRepository);

        ProductAttribute data = new ProductAttribute();
        data.setName(req.name());
        data.setCategory(req.category());
        data.setIsActive(req.is_active());

        GlobalConverter.CmsAdminCreateAtBy(data, user.getId());
        productAttributeRepository.save(data);
    }

    @Override
    public void updateProductAttribute(String id, ProductAttributeModel.@Valid UpdateProductAttributeRequest req) {
        Users user = TreeGetEntity.parsingUserByProjection(ContextPrincipal.getSecureUserId(), userRepository);

        ProductAttribute data = TreeGetEntity.parsingProductAttributeByProjection(id, productAttributeRepository);
        if (data != null) {
            data.setName(req.name() != null ? req.name() : data.getName());
            data.setCategory(req.category() != null ? req.category() : data.getCategory());
            data.setIsActive(req.is_active());

            GlobalConverter.CmsAdminUpdateAtBy(data, user.getId());
            productAttributeRepository.save(data);
        }
    }

    @Override
    public void deleteProductAttribute(String id) {
        ProductAttribute data = TreeGetEntity.parsingProductAttributeByProjection(id, productAttributeRepository);
        productAttributeRepository.updateSoftDelete(data);
    }
}
