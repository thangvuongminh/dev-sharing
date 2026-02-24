package com.example.contentservice.mapper;

import com.example.contentservice.dto.response.ContentDto;
import com.example.contentservice.dto.response.ContentSummaryDto;
import com.example.contentservice.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentMapper {
    ContentDto toContentDto(Content content);
    ContentSummaryDto toContentSummaryDto(Content content);
}
