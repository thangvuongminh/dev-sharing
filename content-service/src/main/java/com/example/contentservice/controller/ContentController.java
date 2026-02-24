package com.example.contentservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.contentservice.dto.request.ContentSearchRequest;
import com.example.contentservice.dto.request.CreateContentRequest;
import com.example.contentservice.dto.request.UpdateContentRequest;
import com.example.contentservice.dto.response.ContentDto;
import com.example.contentservice.dto.response.ContentSummaryDto;
import com.example.contentservice.entity.Content;
import com.example.contentservice.mapper.ContentMapper;
import com.example.contentservice.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.POST;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/contents")
@Tag(name = "Content Management", description = "APIs for managing contents")
public class ContentController {

  ContentService contentService;
  ContentMapper contentMapper;

  @PostMapping
  @Operation(summary = "Create new content", description = "Create a new content as a creator")
  public ResponseEntity<ApiResponse<ContentDto>> createContent(
      @RequestBody CreateContentRequest createContentRequest) {
    Content content = contentService.createContent(createContentRequest);
    ContentDto contentDto = contentMapper.toContentDto(content);
    return ResponseEntity.ok().body(ApiResponse.success(contentDto));
  }

  @GetMapping("/{contentId}")
  @Operation(summary = "Get content by id", description = "Get detailed content information")
  public ResponseEntity<ApiResponse<ContentDto>> getContentById(@PathVariable long contentId) {
    ContentDto contentDto = contentMapper.toContentDto(contentService.getContentById(contentId));
    return ResponseEntity.ok().body(ApiResponse.success(contentDto));
  }

  @PutMapping("/{contentId}")
  @Operation(summary = "Update content by id", description = "Update content existed")
  public ResponseEntity<ApiResponse<ContentDto>> updateContent(@PathVariable long contentId,
      @RequestBody UpdateContentRequest updateContentRequest) {
    Content content = contentService.updateContent(contentId, updateContentRequest);
    ContentDto contentDto = contentMapper.toContentDto(content);
    return ResponseEntity.ok().body(ApiResponse.success(contentDto));
  }

  @DeleteMapping("/{contentId}")
  @Operation(summary = "Delete content by id", description = "Delete content existed")
  public ResponseEntity<ApiResponse<Void>> deleteContent(@PathVariable long contentId) {
    contentService.deleteContentById(contentId);
    return ResponseEntity.ok().body(null);
  }

  @PostMapping("/search/author")
  @Operation(summary = "Search content by author", description = "Search content by author")
  public ResponseEntity<ApiResponse<Page<ContentSummaryDto>>> getContentByAuthor(
      @RequestBody ContentSearchRequest contentSearchRequest) {
    Page<Content> allContents= contentService.searchContentByAuthor(contentSearchRequest);
    Page<ContentSummaryDto> contentSummaryDtos = allContents.map(contentMapper::toContentSummaryDto
    );
    return ResponseEntity.ok().body(ApiResponse.success(contentSummaryDtos));
  }

}
