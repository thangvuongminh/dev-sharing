package com.example.contentservice.service.impl;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.example.contentservice.dto.request.CreateCategoryRequest;
import com.example.contentservice.dto.request.UpdateCategoryRequest;
import com.example.contentservice.entity.Category;
import com.example.contentservice.exception.ExceptionEnum;
import com.example.contentservice.repository.CategoryRepository;
import com.example.contentservice.service.CategoryService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public class CategoryServiceImpl implements CategoryService {
  CategoryRepository categoryRepository;
  @Override
  @Transactional(readOnly = true)
  public Category getCategoryById(Long id) {
    return categoryRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CATEGORY_NOT_FOUND)
    );
  }

  @Override
  @Transactional
  public Category createCategory(CreateCategoryRequest createCategoryRequest) {
    Category category=Category.builder()
        .name(createCategoryRequest.getName())
        .description(createCategoryRequest.getDescription())
        .slug(createCategoryRequest.getSlug())
        .build();
    categoryRepository.save(category);
    return category;
  }

  @Override
  @Transactional
  public Category updateCategory(Long categoryId, UpdateCategoryRequest updateCategoryRequest) {
    Category category=categoryRepository.findById(categoryId).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CATEGORY_NOT_FOUND)
    );
    if(updateCategoryRequest.getName()!=null) {
      category.setName(updateCategoryRequest.getName());
    }
    if(updateCategoryRequest.getDescription()!=null) {
      category.setDescription(updateCategoryRequest.getDescription());
    }
    if(updateCategoryRequest.getSlug()!=null) {
      category.setSlug(updateCategoryRequest.getSlug());
    }
    categoryRepository.save(category);
    return category;
  }
  @Override
  @Transactional
  public void  deleteCategory(Long categoryId) {
    Category category=categoryRepository.findById(categoryId).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CATEGORY_NOT_FOUND)
    );
    categoryRepository.delete(category);
  }

  @Override
  @Transactional
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }
}
