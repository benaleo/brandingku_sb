package com.brandingku.web.controller;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductAttributeModel;
import com.brandingku.web.repository.ProductAttributeRepository;
import com.brandingku.web.response.ApiResponse;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.ProductAttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(ProductAttributeController.urlRoute)
@Tag(name = "product attribute API")
@Slf4j
@SecurityRequirement(name = "Authorization")
public class ProductAttributeController {

    static final String urlRoute = "/cms/v1/product-attribute";

    private final ProductAttributeService productAttributeService;

    @Operation(description = "Get all product attribute")
    @GetMapping
    public ResponseEntity<?> getAllProductAttribute(
            @RequestParam(name = "pages", required = false, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category
    ) {
        try {
            CompilerPagination f = new CompilerPagination(pages, limit, sortBy, direction, keyword);
            ResultPageResponseDTO<ProductAttributeModel.ListProductAttributeResponse> response = productAttributeService.getAllProductAttribute(f, category);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get all product attribute", response));
        } catch (Exception e) {
            log.error("Error get all product attribute : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Get detail product attribute")
    @GetMapping("{id}")
    public ResponseEntity<?> getDetailProductAttribute(@PathVariable("id") String id) {
        try {
            ProductAttributeModel.DetailProductAttributeResponse response = productAttributeService.getDetailProductAttribute(id);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get detail product attribute", response));
        } catch (Exception e) {
            log.error("Error get detail product attribute : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Create product attribute")
    @PostMapping
    public ResponseEntity<ApiResponse> createProductAttribute(@Valid @RequestBody ProductAttributeModel.CreateProductAttributeRequest req) {
        try {
            productAttributeService.createProductAttribute(req);
            return ResponseEntity.created(URI.create(urlRoute)).body(new ApiResponse(true, "Success create product attribute", null));
        } catch (Exception e) {
            log.error("Error create product attribute : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Update product attribute")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateProductAttribute(@PathVariable("id") String id, @Valid @RequestBody ProductAttributeModel.UpdateProductAttributeRequest req) {
        try {
            productAttributeService.updateProductAttribute(id, req);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success update product attribute", null));
        } catch (Exception e) {
            log.error("Error update product attribute : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Delete product attribute")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProductAttribute(@PathVariable("id") String id) {
        try {
            productAttributeService.deleteProductAttribute(id);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success delete product attribute", null));
        } catch (Exception e) {
            log.error("Error delete product attribute : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
