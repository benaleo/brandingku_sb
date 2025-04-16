package com.brandingku.web.service.impl;

import com.brandingku.web.entity.ProductCategory;
import com.brandingku.web.entity.Users;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.ProductCategoryModel;
import com.brandingku.web.model.projection.ProductCategoryIndexProjection;
import com.brandingku.web.repository.ProductCategoryRepository;
import com.brandingku.web.repository.UserRepository;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.util.ContextPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductCategoryServiceImpl productCategoryService;

    private ProductCategory productCategory;
    private Users user;

    @BeforeEach
    void setUp() {
        productCategory = new ProductCategory();
        productCategory.setName("Test Category");
        productCategory.setSlug("test-category");
        productCategory.setDescription("Test Description");
        productCategory.setSecureId("test123");

        user = new Users();
        user.setId(1L);
        user.setSecureId("user123");
    }

    @Test
    void getAllProductCategory_ShouldReturnPageResponse() {
        // Arrange
        CompilerPagination pagination = new CompilerPagination(0, 10, "name", "asc", "test");
        ProductCategoryIndexProjection projection = mock(ProductCategoryIndexProjection.class);
        Page<ProductCategoryIndexProjection> page = new PageImpl<>(Collections.singletonList(projection));

        when(productCategoryRepository.findDataByKeyword(anyString(), any(Pageable.class)))
                .thenReturn(page);

        // Act
        ResultPageResponseDTO<ProductCategoryModel.ListProductCategoryResponse> result =
                productCategoryService.getAllProductCategory(pagination);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalItems());
        verify(productCategoryRepository, times(2)).findDataByKeyword(anyString(), any(Pageable.class));
    }

    @Test
    void getDetailProductCategory_ShouldReturnDetailResponse() {
        // Arrange
        when(productCategoryRepository.findBySecureId(anyString())).thenReturn(Optional.of(productCategory));

        // Act
        ProductCategoryModel.DetailProductCategoryResponse result =
                productCategoryService.getDetailProductCategory("test123");

        // Assert
        assertNotNull(result);
        assertEquals("Test Category", result.name());
        assertEquals("test-category", result.slug());
        assertEquals("Test Description", result.description());
    }

    @Test
    void createProductCategory_ShouldSaveNewCategory() {
        // Arrange
        ProductCategoryModel.CreateProductCategoryRequest request =
                new ProductCategoryModel.CreateProductCategoryRequest("New Category", "New Description");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productCategoryRepository.count()).thenReturn(0L);

        // Mock ContextPrincipal
        try (var mocked = mockStatic(ContextPrincipal.class)) {
            mocked.when(ContextPrincipal::getId).thenReturn(1L);

            // Act
            productCategoryService.createProductCategory(request);

            // Assert
            verify(productCategoryRepository).save(any(ProductCategory.class));
        }
    }

    @Test
    void updateProductCategory_ShouldUpdateExistingCategory() {
        // Arrange
        ProductCategoryModel.UpdateProductCategoryRequest request =
                new ProductCategoryModel.UpdateProductCategoryRequest("Updated Name", "Updated Description");

        when(productCategoryRepository.findBySecureId(anyString())).thenReturn(Optional.of(productCategory));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Mock ContextPrincipal
        try (var mocked = mockStatic(ContextPrincipal.class)) {
            mocked.when(ContextPrincipal::getId).thenReturn(1L);

            // Act
            productCategoryService.updateProductCategory("test123", request);

            // Assert
            verify(productCategoryRepository).save(productCategory);
            assertEquals("Updated Name", productCategory.getName());
            assertEquals("Updated Description", productCategory.getDescription());
        }
    }

    @Test
    void deleteProductCategory_ShouldDeleteCategory() {
        // Arrange
        when(productCategoryRepository.findBySecureId(anyString())).thenReturn(Optional.of(productCategory));

        // Act
        productCategoryService.deleteProductCategory("test123");

        // Assert
        verify(productCategoryRepository).softDelete(productCategory);
        verify(productCategoryRepository, never()).delete(productCategory);
    }
}
