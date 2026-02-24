package com.example.contentservice.service.impl;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.example.contentservice.entity.Category;
import com.example.contentservice.exception.ExceptionEnum;
import com.example.contentservice.repository.CategoryRepository;
import com.example.contentservice.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {
  CategoryRepository categoryRepository;
  @Override
  public Category getCategoryById(Long id) {
    return categoryRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CATEGORY_NOT_FOUND)
    );
  }
}
