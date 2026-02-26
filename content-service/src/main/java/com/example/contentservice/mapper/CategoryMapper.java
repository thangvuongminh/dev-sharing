package com.example.contentservice.mapper;

import com.example.contentservice.dto.response.CategoryDto;
import com.example.contentservice.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
  List<CategoryDto> toCategoryDto(List<Category> categories);
  CategoryDto toCategoryDto(Category category);
}
