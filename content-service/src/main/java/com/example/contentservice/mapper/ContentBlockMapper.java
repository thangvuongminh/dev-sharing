package com.example.contentservice.mapper;

import com.example.contentservice.dto.response.ContentBlockDto;
import com.example.contentservice.entity.ContentBlock;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentBlockMapper {
  ContentBlockDto toBlockDto(ContentBlock contentBlock);
}
