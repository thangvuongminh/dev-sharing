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

  @PostMapping("/search/content/author")
  @Operation(summary = "Search content by author", description = "Search content by author")
  public ResponseEntity<ApiResponse<Page<ContentSummaryDto>>> getContentByAuthor(
      @RequestBody ContentSearchRequest contentSearchRequest) {
    Page<Content> allContents= contentService.searchContentByAuthor(contentSearchRequest);
    Page<ContentSummaryDto> contentSummaryDtos = allContents.map(contentMapper::toContentSummaryDto
    );
    return ResponseEntity.ok().body(ApiResponse.success(contentSummaryDtos));
  }
  @PostMapping("/search/content/users")
  @Operation(summary = "Search content by any users", description = "Search content by any users")
  public ResponseEntity<ApiResponse<Page<ContentSummaryDto>>> getContentByAnyUsers(
      @RequestBody ContentSearchRequest contentSearchRequest) {
    Page<Content> allContents= contentService.searchContentAnyUsers(contentSearchRequest);
    Page<ContentSummaryDto> contentSummaryDtos = allContents.map(contentMapper::toContentSummaryDto
    );
    return ResponseEntity.ok().body(ApiResponse.success(contentSummaryDtos));
  }
  @PostMapping("/{contentId}/publish")
  @Operation(summary = "Publish content",description = "Publish content for transaction")
  public ResponseEntity<ApiResponse<ContentDto>> publishContent(@PathVariable long contentId) {
    Content content = contentService.publishContent(contentId);
    ContentDto contentDto = contentMapper.toContentDto(content);
    return ResponseEntity.ok().body(ApiResponse.success(contentDto));
  }
  @PostMapping("/{contentId}/archive")
  @Operation(summary = "Publish content",description = "Publish content for transaction")
  public ResponseEntity<ApiResponse<ContentDto>> archiveContent(@PathVariable long contentId) {
    Content content = contentService.archiveContent(contentId);
    ContentDto contentDto = contentMapper.toContentDto(content);
    return ResponseEntity.ok().body(ApiResponse.success(contentDto));
  }
  @PostMapping("/{contentId}/submit-review")
  @Operation(summary = "submit reviews content",description = "submit reviews content")
  public ResponseEntity<ApiResponse<ContentDto>> submitContentForReview(@PathVariable long contentId) {
    Content content = contentService.submitForReview(contentId);
    ContentDto contentDto = contentMapper.toContentDto(content);
    return ResponseEntity.ok().body(ApiResponse.success(contentDto));
  }
  @PostMapping("{contentId}/view")
  @Operation(summary = "Increment view count",description = "Track content view (public)")
  public ResponseEntity<ApiResponse<Void>> incrementViewCount(@PathVariable long contentId) {
    contentService.incrementViewCount(contentId);
    return ResponseEntity.ok().body(ApiResponse.success(null));
  }
  @PostMapping("{contentId}/increment-purchase-count")
  @Operation(summary = "Increment purchase count",description = "Called by purchase-service after successful purchase (internal API)")
  public ResponseEntity<ApiResponse<Void>> incrementPurchaseCount(@PathVariable long contentId) {
    contentService.incrementPurchaseCount(contentId);
    return ResponseEntity.ok().body(ApiResponse.success(null));
  }
}
