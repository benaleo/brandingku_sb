package com.brandingku.web.controller;

import com.brandingku.web.model.AppLandingFeaturedCategoryResponse;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.response.ApiResponse;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(AppLandingController.urlRoute)
@Tag(name = "landing page API")
@Slf4j
public class AppLandingController {

    static final String urlRoute = "/api/v1";

    private final ProductCategoryService productCategoryService;

    @GetMapping("/featured-category")
    public ResponseEntity<?> getFeaturedCategory(
            @RequestParam(name = "pages", required = false, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "slug", required = false) String slug
    ) {
        try{
            CompilerPagination f = new CompilerPagination(pages, limit, sortBy, direction, keyword);
            ResultPageResponseDTO<AppLandingFeaturedCategoryResponse> response = productCategoryService.getFeaturedCategory(f, slug);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get featured category", response));
        } catch (Exception e){
            log.error("Error get featured category : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
