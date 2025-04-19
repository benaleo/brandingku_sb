package com.brandingku.web.service.impl;

import com.brandingku.web.entity.Product;
import com.brandingku.web.entity.Users;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductModel;
import com.brandingku.web.model.search.ListOfFilterPagination;
import com.brandingku.web.model.search.SavedKeywordAndPageable;
import com.brandingku.web.repository.ProductCategoryRepository;
import com.brandingku.web.repository.ProductRepository;
import com.brandingku.web.repository.UserRepository;
import com.brandingku.web.response.PageCreateReturn;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final UrlConverterService urlConverterService;

    @Override
    public ResultPageResponseDTO<ProductModel.ListProductResponse> getAllProduct(CompilerPagination f) {
        ListOfFilterPagination filter = new ListOfFilterPagination(f.keyword());
        SavedKeywordAndPageable set = GlobalConverter.appsCreatePageable(f.pages(), f.limit(), f.sortBy(), f.direction(), f.keyword(), filter);

        // First page result (get total count)
        Page<Product> firstResult = productRepository.findDataByKeyword(set.keyword(), set.pageable());

        // Use a correct Pageable for fetching the next page
        Pageable pageable = GlobalConverter.oldSetPageable(f.pages(), f.limit(), f.sortBy(), f.direction(), firstResult, null);
        Page<Product> pageResult = productRepository.findDataByKeyword(set.keyword(), pageable);

        // Map the data to the DTOs
        List<ProductModel.ListProductResponse> dtos = pageResult.stream().map((c) -> {
            ProductModel.ListProductResponse dto = new ProductModel.ListProductResponse();
            dto.setName(c.getName());
            dto.setSlug(c.getSlug());
            dto.setDescription(c.getDescription());
            dto.setHighlight_description(c.getHighlightDescription());
            dto.setPrice(c.getPrice());
            dto.setDiscount(c.getDiscount());
            dto.setDiscount_type(c.getDiscountType());
            dto.setQuantity(c.getQuantity());
            dto.setImage(c.getFirstImage());
            dto.setHighlight_image(c.getHighlightImage());
            dto.setIs_highlight(c.getIsHighlight());
            dto.setIs_recommended(c.getIsRecommended());
            dto.setIs_upsell(c.getIsUpsell());
            dto.setCategory_name(c.getCategory() == null ? null : c.getCategory().getName());

            GlobalConverter.CmsIDTimeStampResponseAndId(dto, c, userRepository);
            return dto;
        }).collect(Collectors.toList());

        return PageCreateReturn.create(
                pageResult,
                dtos
        );
    }

    @Override
    public ProductModel.DetailProductResponse getDetailProduct(String id) {
        Product data = productRepository.findBySecureId(id).orElse(null);
        return new ProductModel.DetailProductResponse(
                data == null ? null : data.getName(),
                data == null ? null : data.getSlug(),
                data == null ? null : data.getDescription(),
                data == null ? null : data.getPrice(),
                data == null ? null : data.getDiscount(),
                data == null ? null : data.getDiscountType(),
                data == null ? null : data.getQuantity(),
                data == null ? null : data.getIsRecommended(),
                data == null ? null : data.getIsUpsell(),
                data == null ? null : (data.getCategory() == null ? null : data.getCategory().getName()),
                data == null ? null : (data.getCategory() == null ? null : data.getCategory().getSecureId())
        );
    }

    @Override
    public void createProduct(ProductModel.@Valid CreateProductRequest req) {
        Users user = userRepository.findById(ContextPrincipal.getId()).orElse(null);

        Product data = new Product();
        data.setName(req.name());
        data.setSlug(req.slug());
        data.setDescription(req.description());
        data.setPrice(req.price());
        data.setDiscount(req.discount());
        data.setDiscountType(req.discount_type());
        data.setQuantity(req.quantity());
        data.setIsRecommended(req.is_recommended());
        data.setIsUpsell(req.is_upsell());
        data.setCategory(productCategoryRepository.findBySecureId(req.category_id()).orElse(null));

        GlobalConverter.CmsAdminCreateAtBy(data, user != null ? user.getId() : null);
        productRepository.save(data);
    }

    @Override
    public void updateProduct(String id, ProductModel.@Valid UpdateProductRequest req) {
        Users user = userRepository.findById(ContextPrincipal.getId()).orElse(null);

        Product data = productRepository.findBySecureId(id).orElse(null);
        if (data != null) {
            data.setName(req.name() != null ? req.name() : data.getName());
            data.setSlug(req.slug() != null ? req.slug() : data.getSlug());
            data.setDescription(req.description() != null ? req.description() : data.getDescription());
            data.setPrice(req.price() != null ? req.price() : data.getPrice());
            data.setDiscount(req.discount() != null ? req.discount() : data.getDiscount());
            data.setDiscountType(req.discount_type() != null ? req.discount_type() : data.getDiscountType());
            data.setQuantity(req.quantity() != null ? req.quantity() : data.getQuantity());
            data.setIsRecommended(req.is_recommended() != null ? req.is_recommended() : data.getIsRecommended());
            data.setIsUpsell(req.is_upsell() != null ? req.is_upsell() : data.getIsUpsell());
            data.setCategory(productCategoryRepository.findBySecureId(req.category_id() != null ? req.category_id() : data.getCategory().getSecureId()).orElse(null));

            GlobalConverter.CmsAdminUpdateAtBy(data, user != null ? user.getId() : null);
            productRepository.save(data);
        }
    }

    @Override
    public void deleteProduct(String id) {
        Product data = productRepository.findBySecureId(id).orElse(null);
        productRepository.softDelete(data);
    }

    @Override
    public void postHighlightProduct(String id, MultipartFile file, String description, Boolean isHighlight) throws IOException {
        Product data = productRepository.findBySecureId(id).orElse(null);
        if (data != null) {
            data.setHighlightImage(file == null ? data.getHighlightImage() : urlConverterService.saveUrlImageProduct(file));
            data.setHighlightDescription(description);
            data.setIsHighlight(isHighlight == null ? data.getIsHighlight() : isHighlight);
            productRepository.save(data);
        }
    }
}
