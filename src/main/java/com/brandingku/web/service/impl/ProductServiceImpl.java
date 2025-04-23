package com.brandingku.web.service.impl;

import com.brandingku.web.entity.*;
import com.brandingku.web.exception.BadRequestException;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductModel;
import com.brandingku.web.model.dto.ProductAdditionalDetailResponse;
import com.brandingku.web.model.dto.ProductAttributeDetailResponse;
import com.brandingku.web.model.search.ListOfFilterPagination;
import com.brandingku.web.model.search.SavedKeywordAndPageable;
import com.brandingku.web.repository.*;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductGalleryRepository productGalleryRepository;
    private final UserRepository userRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductAdditionalRepository productAdditionalRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAdditionalHasAttributeRepository productAdditionalHasAttributeRepository;
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
        List<ProductModel.ListProductResponse> dtos = pageResult.stream().map(this::parseIndexProductResponse).collect(Collectors.toList());

        return PageCreateReturn.create(
                pageResult,
                dtos
        );
    }

    @Override
    public ProductModel.DetailProductResponse getDetailProduct(String id) {
        Product data = productRepository.findBySecureId(id).orElse(null);

        List<ProductAdditionalDetailResponse> additionalDetailResponses = getProductAdditionalDetailResponses(data);

        return new ProductModel.DetailProductResponse(
                data == null ? null : data.getName(),
                data == null ? null : data.getSlug(),
                data == null ? null : data.getDescription(),
                data == null ? null : data.getHighlightDescription(),
                data == null ? null : data.getHighlightImage(),
                data == null ? null : data.getIsHighlight(),
                data == null ? null : data.getIsRecommended(),
                data == null ? null : data.getIsUpsell(),
                data == null ? null : (data.getCategory() == null ? null : data.getCategory().getName()),
                data == null ? null : (data.getCategory() == null ? null : data.getCategory().getSecureId()),
                additionalDetailResponses
        );
    }

    private List<ProductAdditionalDetailResponse> getProductAdditionalDetailResponses(Product data) {
        if (data == null || data.getAdditional().isEmpty()) {
            return new ArrayList<>();
        }

        return data.getAdditional().stream().map(additional -> {
            List<ProductAttributeDetailResponse> listAttributes = productAdditionalHasAttributeRepository
                    .findAllByAdditional(additional).stream()
                    .map(attr -> new ProductAttributeDetailResponse(
                            attr.getAttribute().getSecureId(),
                            attr.getAttribute().getCategory(),
                            attr.getAttribute().getName()))
                    .collect(Collectors.toList());

            return new ProductAdditionalDetailResponse(
                    additional.getSecureId(),
                    additional.getPrice(),
                    additional.getMoq(),
                    additional.getStock(),
                    additional.getDiscount(),
                    additional.getDiscountType(),
                    listAttributes
            );
        }).collect(Collectors.toList());
    }

    @Override
    public void createProduct(ProductModel.@Valid CreateProductRequest req) {
        Users user = userRepository.findById(ContextPrincipal.getId()).orElse(null);

        Product data = new Product();
        data.setName(req.name());
        data.setSlug(req.slug() != null ? req.slug() : GlobalConverter.makeSlug(req.name()));
        data.setDescription(req.description());
        data.setIsRecommended(req.is_recommended());
        data.setIsUpsell(req.is_upsell());
        data.setCategory(productCategoryRepository.findBySecureId(req.category_id()).orElse(null));
        data.setIsHighlight(Boolean.TRUE.equals(req.is_highlight()));

        if (Boolean.TRUE.equals(req.is_highlight())) {
            data.setHighlightImage(req.highlight_image());
            data.setHighlightDescription(req.highlight_description());
        }

        GlobalConverter.CmsAdminCreateAtBy(data, user != null ? user.getId() : null);
        Product savedProduct = productRepository.save(data);

        req.additionals().forEach(additional -> {
            ProductAdditional newAdditional = new ProductAdditional(
                    savedProduct,
                    additional.price(),
                    additional.moq(),
                    additional.stock(),
                    additional.discount(),
                    additional.discount_type()
            );

            List<ProductAttribute> attributes = productAttributeRepository.findAllBySecureIdIn(
                    additional.attributes().stream().map(ProductAttributeDetailResponse::id).collect(Collectors.toList())
            );

            attributes.forEach(attr -> productAdditionalHasAttributeRepository.save(
                    new ProductAdditionalHasAttribute(newAdditional, attr)
            ));
        });
    }

    @Override
    public void updateProduct(String id, ProductModel.@Valid UpdateProductRequest req) {
        Users user = userRepository.findById(ContextPrincipal.getId()).orElse(null);

        Product data = productRepository.findBySecureId(id).orElse(null);
        if (data != null) {
            data.setName(req.name() != null ? req.name() : data.getName());
            data.setSlug(req.slug() != null ? req.slug() : data.getSlug());
            data.setDescription(req.description() != null ? req.description() : data.getDescription());
            data.setIsRecommended(req.is_recommended() != null ? req.is_recommended() : data.getIsRecommended());
            data.setIsUpsell(req.is_upsell() != null ? req.is_upsell() : data.getIsUpsell());
            data.setCategory(productCategoryRepository.findBySecureId(
                    req.category_id() != null ? req.category_id() :
                            (data.getCategory() != null ? data.getCategory().getSecureId() : null)
            ).orElse(null));

            data.setIsHighlight(false);
            if (req.is_highlight() != null && req.is_highlight()) {
                data.setHighlightImage(req.highlight_image());
                data.setHighlightDescription(req.highlight_description());
                data.setIsHighlight(true);
            }

            GlobalConverter.CmsAdminUpdateAtBy(data, user != null ? user.getId() : null);
            Product updatedProduct = productRepository.save(data);

            if (req.additionals() != null) {
                productAdditionalHasAttributeRepository.deleteAllByAdditionalIn(updatedProduct.getAdditional());

                req.additionals().forEach(additional -> {

                    ProductAdditional newAdditional = productAdditionalRepository.findBySecureId(additional.id()).orElseGet(
                            () -> new ProductAdditional(updatedProduct, additional.price(), additional.moq(), additional.stock(), additional.discount(), additional.discount_type())
                    );

                    List<ProductAttribute> attributes = productAttributeRepository.findAllBySecureIdIn(
                            additional.attributes().stream().map(ProductAttributeDetailResponse::id).collect(Collectors.toList())
                    );

                    attributes.forEach(attr -> productAdditionalHasAttributeRepository.save(
                            new ProductAdditionalHasAttribute(newAdditional, attr)
                    ));
                });
            }
        }
    }

    @Override
    public void deleteProduct(String id) {
        Product data = productRepository.findBySecureId(id).orElse(null);
        productRepository.softDelete(data);
    }

    @Override
    public ProductModel.ListProductResponse postHighlightProduct(String id, String file, String description, Boolean isHighlight) throws IOException {
        Product data = productRepository.findBySecureId(id).orElse(null);
        if (data != null) {
            data.setHighlightImage(file == null ? data.getHighlightImage() : file);
            data.setHighlightDescription(description);
            data.setIsHighlight(isHighlight == null ? data.getIsHighlight() : isHighlight);
            productRepository.save(data);

            return parseIndexProductResponse(data);
        }
        throw new BadRequestException("Product not found");
    }

    @Override
    public ProductModel.ListProductResponse postGalleryProduct(String id, List<String> newFile, List<String> removeId) throws IOException {
        Product data = productRepository.findBySecureId(id).orElse(null);
        if (data != null) {
            if (newFile != null) {
                for (String url : newFile) {
                    ProductGallery gallery = new ProductGallery();
                    gallery.setProduct(data);
                    gallery.setUrlFile(url);
                    productGalleryRepository.save(gallery);
                }
            }
            if (removeId != null) {
                productGalleryRepository.deleteAllBySecureIdIn(removeId);
            }
            return parseIndexProductResponse(data);
        }
        throw new BadRequestException("Product not found");
    }

    // parse index response
    private ProductModel.ListProductResponse parseIndexProductResponse(Product c) {
        ProductModel.ListProductResponse dto = new ProductModel.ListProductResponse();
        dto.setName(c.getName());
        dto.setSlug(c.getSlug());
        dto.setDescription(c.getDescription());
        dto.setHighlight_description(c.getHighlightDescription());
        dto.setImage(c.getFirstImage());
        dto.setHighlight_image(c.getHighlightImage());
        dto.setIs_highlight(c.getIsHighlight());
        dto.setIs_recommended(c.getIsRecommended());
        dto.setIs_upsell(c.getIsUpsell());

        dto.setCategory_name(c.getCategory() == null ? null : c.getCategory().getName());
        dto.setGalleries(c.getListGallery().stream()
                .map(g -> new ProductModel.ListProductResponse.ProductGalleryOptions(g.getSecureId(), g.getUrlFile()))
                .collect(Collectors.toList()));

        GlobalConverter.CmsIDTimeStampResponseAndId(dto, c, userRepository);
        return dto;
    }
}
