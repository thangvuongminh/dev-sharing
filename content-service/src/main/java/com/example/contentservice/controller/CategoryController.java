package com.example.contentservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.contentservice.dto.response.CategoryDto;
import com.example.contentservice.entity.Category;
import com.example.contentservice.mapper.CategoryMapper;
import com.example.contentservice.repository.CategoryRepository;
import com.example.contentservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
