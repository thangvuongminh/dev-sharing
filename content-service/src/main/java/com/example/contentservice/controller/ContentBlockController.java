package com.example.contentservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.contentservice.dto.request.CreateBlockRequest;
import com.example.contentservice.dto.request.UpdateContentBlockRequest;
import com.example.contentservice.dto.response.ContentBlockDto;
import com.example.contentservice.entity.ContentBlock;
import com.example.contentservice.mapper.ContentBlockMapper;
import com.example.contentservice.service.ContentBlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contents/{contentId}/blocks")
@Tag(name = "Content block management", description = "APIs for managing content blocks (tree structure)")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentBlockController {

  ContentBlockService contentBlockService;
  ContentBlockMapper contentBlockMapper;

  @PostMapping
  @Operation(summary = "Create blocks", description = "Create a new block in a content. Blocks can be nested (parent-child relationship).")
  public ResponseEntity<ApiResponse<ContentBlockDto>> createBlock(
      @PathVariable("contentId") Long contentId,
      @RequestBody CreateBlockRequest createBlockRequest) {
    ContentBlock contentBlock = contentBlockService.createBlock(contentId, createBlockRequest);
    ContentBlockDto contentBlockDto = contentBlockMapper.toBlockDto(contentBlock);
    return ResponseEntity.ok().body(ApiResponse.success(contentBlockDto));
  }

  @PutMapping("{blockId}")
  @Operation(summary = "Updates blocks", description = "Update an existing block's content and properties"
  )
  public ResponseEntity<ApiResponse<ContentBlockDto>> updateContentBlock(
      @PathVariable("contentId") Long contentId, @PathVariable("blockId") Long blockId,
      @RequestBody UpdateContentBlockRequest updateContentBlockRequest) {
    ContentBlock contentBlock = contentBlockService.updateContentBlock(contentId, blockId,
        updateContentBlockRequest);
    ContentBlockDto contentBlockDto = contentBlockMapper.toBlockDto(contentBlock);
    return ResponseEntity.ok().body(ApiResponse.success(contentBlockDto));
  }
  @DeleteMapping("{blockId}")
  @Operation(summary = "Delete blocks", description = "Delete an existing block's content"
  )
  public ResponseEntity<ApiResponse<ContentBlockDto>> deleteContentBlock(
      @PathVariable("contentId") Long contentId, @PathVariable("blockId") Long blockId,
      @RequestBody UpdateContentBlockRequest updateContentBlockRequest) {
    contentBlockService.deleteContentBlock(contentId, blockId);
    return ResponseEntity.ok().body(ApiResponse.success(null));
  }
  @GetMapping("{blockId}")
  @Operation(summary = "Get blocks",description = "Get an existing block's content")
  public ResponseEntity<ApiResponse<ContentBlockDto>> getContentBlock(
      @PathVariable("contentId") Long contentId, @PathVariable("blockId") Long blockId
  ){
    ContentBlock contentBlock = contentBlockService.getContentBlock(contentId, blockId);
    ContentBlockDto contentBlockDto = contentBlockMapper.toBlockDto(contentBlock);
    return ResponseEntity.ok().body(ApiResponse.success(contentBlockDto));
  }
}
