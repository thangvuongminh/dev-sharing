package com.example.contentservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.contentservice.dto.request.CreateCategoryRequest;
import com.example.contentservice.dto.request.UpdateCategoryRequest;
import com.example.contentservice.dto.response.CategoryDto;
import com.example.contentservice.entity.Category;
import com.example.contentservice.mapper.CategoryMapper;
import com.example.contentservice.repository.CategoryRepository;
import com.example.contentservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category management",description = "APIs for managing categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
  CategoryService categoryService;
  CategoryMapper categoryMapper;
  @GetMapping
  @Operation(summary = "Get all categories", description = "Get list of all available categories")
  public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
    List<Category> categories = categoryService.getAllCategories();
    List<CategoryDto> categoryDtos=categoryMapper.toCategoryDto(categories);
    return ResponseEntity.ok(ApiResponse.success(categoryDtos));
  }
  @GetMapping("/{categoryId}")
  @Operation(summary = "Get category by ID", description = "Get category details by ID")
  public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Long categoryId) {
    Category category = categoryService.getCategoryById(categoryId);
    CategoryDto categoryDto=categoryMapper.toCategoryDto(category);
    return ResponseEntity.ok(ApiResponse.success(categoryDto));
  }
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Create category", description = "Create a new category (Admin only)")
  public ResponseEntity<ApiResponse<CategoryDto>> createCategory(
      @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
    Category category = categoryService.createCategory(createCategoryRequest);
    CategoryDto categoryDto=categoryMapper.toCategoryDto(category);
    return ResponseEntity.ok(ApiResponse.success(categoryDto));
  }

  @PutMapping("/{categoryId}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Update category", description = "Update an existing category (Admin only)")
  public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
      @PathVariable Long categoryId,
      @Valid @RequestBody UpdateCategoryRequest request) {
    Category category = categoryService.updateCategory(categoryId, request);
    CategoryDto categoryDto=categoryMapper.toCategoryDto(category);
    return ResponseEntity.ok(ApiResponse.success(categoryDto));
  }

  @DeleteMapping("/{categoryId}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Delete category", description = "Delete a category (Admin only)")
  public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
