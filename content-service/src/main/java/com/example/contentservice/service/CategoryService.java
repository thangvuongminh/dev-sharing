package com.example.contentservice.service;

import com.example.contentservice.dto.request.CreateCategoryRequest;
import com.example.contentservice.dto.request.UpdateCategoryRequest;
import com.example.contentservice.entity.Category;
import java.util.List;

public interface CategoryService {
  // crud Admin only
  public Category getCategoryById(Long id);
  public Category createCategory(CreateCategoryRequest createCategoryRequest);
  public Category updateCategory(Long categoryId,UpdateCategoryRequest updateCategoryRequest);
  public void  deleteCategory(Long categoryId);
  public List<Category> getAllCategories();
}
