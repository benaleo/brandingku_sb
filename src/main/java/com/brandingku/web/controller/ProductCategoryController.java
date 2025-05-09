package com.brandingku.web.controller;

import com.brandingku.web.entity.ProductCategory;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductCategoryModel;
import com.brandingku.web.repository.ProductCategoryRepository;
import com.brandingku.web.response.ApiResponse;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.ProductCategoryService;
import com.brandingku.web.util.TreeGetEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(ProductCategoryController.urlRoute)
@Tag(name = "product category API")
@Slf4j
@SecurityRequirement(name = "Authorization")
public class ProductCategoryController {

    static final String urlRoute = "/cms/v1/product-category";

    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;

    @Operation(description = "Get all product category")
    @GetMapping
    public ResponseEntity<?> getAllProductCategory(
            @RequestParam(name = "pages", required = false, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        try {
            CompilerPagination f = new CompilerPagination(pages, limit, sortBy, direction, keyword);
            ResultPageResponseDTO<ProductCategoryModel.ListProductCategoryResponse> response = productCategoryService.getAllProductCategory(f);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get all product category", response));
        } catch (Exception e) {
            log.error("Error get all product category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Get detail product category")
    @GetMapping("{id}")
    public ResponseEntity<?> getDetailProductCategory(@PathVariable("id") String id) {
        try {
            ProductCategoryModel.DetailProductCategoryResponse response = productCategoryService.getDetailProductCategory(id);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get detail product category", response));
        } catch (Exception e) {
            log.error("Error get detail product category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Create product category")
    @PostMapping
    public ResponseEntity<ApiResponse> createProductCategory(@Valid @RequestBody ProductCategoryModel.CreateProductCategoryRequest req) {
        try {
            productCategoryService.createProductCategory(req);
            return ResponseEntity.created(URI.create(urlRoute)).body(new ApiResponse(true, "Success create product category", null));
        } catch (Exception e) {
            log.error("Error create product category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

//    @Operation(description = "Post image product category")
//    @PutMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ApiResponse> postImageProductCategory(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {
//        try {
//            productCategoryService.postImageProductCategory(id, file);
//            return ResponseEntity.ok().body(new ApiResponse(true, "Success post image product category", null));
//        } catch (Exception e) {
//            log.error("Error post image product category : {}", e.getMessage(), e);
//            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
//        }
//    }

    @Operation(description = "Post image product category")
    @PutMapping(value = "{id}/image")
    public ResponseEntity<ApiResponse> postImageProductCategory(@PathVariable("id") String id, @RequestParam("file") String file) {
        try {
            productCategoryService.postImageProductCategory(id, file);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success post image product category", null));
        } catch (Exception e) {
            log.error("Error post image product category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Update product category")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateProductCategory(@PathVariable("id") String id, @Valid @RequestBody ProductCategoryModel.UpdateProductCategoryRequest req) {
        try {
            productCategoryService.updateProductCategory(id, req);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success update product category", null));
        } catch (Exception e) {
            log.error("Error update product category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Delete product category")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProductCategory(@PathVariable("id") String id) {
        try {
            productCategoryService.deleteProductCategory(id);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success delete product category", null));
        } catch (Exception e) {
            log.error("Error delete product category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
