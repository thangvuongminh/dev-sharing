package com.example.contentservice.service;

import com.example.contentservice.dto.request.CreateBlockRequest;
import com.example.contentservice.dto.request.UpdateContentBlockRequest;
import com.example.contentservice.entity.ContentBlock;

public interface ContentBlockService {
  public ContentBlock createBlock(Long contentId,CreateBlockRequest createBlockRequest);
  public ContentBlock updateContentBlock(Long contentId,Long blockId, UpdateContentBlockRequest updateContentBlockRequest);
  public void deleteContentBlock(Long contentId,Long blockId);
  public ContentBlock getContentBlock(Long contentId,Long blockId);
}
