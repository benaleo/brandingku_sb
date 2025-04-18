package com.brandingku.web.controller;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductModel;
import com.brandingku.web.service.ProductService;
import com.brandingku.web.repository.ProductRepository;
import com.brandingku.web.response.ApiResponse;
import com.brandingku.web.response.ResultPageResponseDTO;
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
@RequestMapping(ProductController.urlRoute)
@Tag(name = "product API")
@Slf4j
@SecurityRequirement(name = "Authorization")
public class ProductController {

    static final String urlRoute = "/cms/v1/product";

    private final ProductService productService;
    private final ProductRepository productRepository;

    @Operation(description = "Get all product")
    @GetMapping
    public ResponseEntity<?> getAllProduct(
            @RequestParam(name = "pages", required = false, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        try {
            CompilerPagination f = new CompilerPagination(pages, limit, sortBy, direction, keyword);
            ResultPageResponseDTO<ProductModel.ListProductResponse> response = productService.getAllProduct(f);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get all product", response));
        } catch (Exception e) {
            log.error("Error get all product : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Get detail product")
    @GetMapping("{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable("id") String id) {
        try {
            ProductModel.DetailProductResponse response = productService.getDetailProduct(id);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get detail product", response));
        } catch (Exception e) {
            log.error("Error get detail product : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Create product")
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductModel.CreateProductRequest req) {
        try {
            productService.createProduct(req);
            return ResponseEntity.created(URI.create(urlRoute)).body(new ApiResponse(true, "Success create product", null));
        } catch (Exception e) {
            log.error("Error create product : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Update product")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") String id, @Valid @RequestBody ProductModel.UpdateProductRequest req) {
        try {
            productService.updateProduct(id, req);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success update product", null));
        } catch (Exception e) {
            log.error("Error update product : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Delete product")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success delete product", null));
        } catch (Exception e) {
            log.error("Error delete product : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
