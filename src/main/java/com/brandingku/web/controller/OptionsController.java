package com.brandingku.web.controller;

import com.brandingku.web.model.OptionResponse;
import com.brandingku.web.response.ApiResponse;
import com.brandingku.web.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(OptionsController.urlRoute)
@Tag(name = "options API")
@Slf4j
@SecurityRequirement(name = "Authorization")
public class OptionsController {

    static final String urlRoute = "/cms/v1/option";

    private final OptionService optionService;

    @Operation(description = "Get all product")
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<OptionResponse> response = optionService.getListProducts();
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get all product", response));
        } catch (Exception e) {
            log.error("Error get all product : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Get all product category")
    @GetMapping("/product-categories")
    public ResponseEntity<?> getAllProductCategories() {
        try {
            List<OptionResponse> response = optionService.getListProductCategories();
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get all product category", response));
        } catch (Exception e) {
            log.error("Error get all product category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }


}
