package com.example.contentservice.service.impl;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.example.contentservice.dto.request.CreateBlockRequest;
import com.example.contentservice.dto.request.MoveBlockRequest;
import com.example.contentservice.dto.request.UpdateContentBlockRequest;
import com.example.contentservice.entity.BlockType;
import com.example.contentservice.entity.Content;
import com.example.contentservice.entity.ContentBlock;
import com.example.contentservice.exception.ExceptionEnum;
import com.example.contentservice.repository.ContentBlockRepository;
import com.example.contentservice.repository.ContentRepository;
import com.example.contentservice.service.ContentBlockService;
import com.example.contentservice.utils.UserContentExtractor;
import java.time.Instant;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class ContentBlockServiceImpl implements ContentBlockService {

  ContentRepository contentRepository;
  ContentBlockRepository contentBlockRepository;

  @Override
  @Transactional
  public ContentBlock createBlock(Long contentId, CreateBlockRequest createBlockRequest) {
    Content content = contentRepository.findById(contentId)
        .orElseThrow(() -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND));
    if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
    }
    ContentBlock parentContentBlock = null;
    if (createBlockRequest.getParentBlockId() != null) {
      parentContentBlock = contentBlockRepository.findById(createBlockRequest.getParentBlockId())
          .orElseThrow(() -> new DevSharingException(ExceptionEnum.BLOCK_NOT_FOUND));
      if (!parentContentBlock.getContent().getId().equals(contentId)) {
        throw new DevSharingException(ExceptionEnum.PARENT_BLOCK_NOT_IN_SAME_CONTENT);
      }
    }
    adjustPositionBlock(contentId, createBlockRequest.getParentBlockId(),
        createBlockRequest.getPosition(), false);
    ContentBlock contentBlock = ContentBlock.builder().content(content)
        .parentBlock(parentContentBlock).textContent(createBlockRequest.getTextContent())
        .position(createBlockRequest.getPosition()).isFree(createBlockRequest.getIsFree())
        .properties(createBlockRequest.getProperties()).type(createBlockRequest.getType())
        .createdAt(Instant.now()).updatedAt(Instant.now()).build();
    contentBlockRepository.save(contentBlock);
    return contentBlock;
  }

  @Override
  @Transactional
  public ContentBlock updateContentBlock(Long contentId, Long blockId,
      UpdateContentBlockRequest updateContentBlockRequest) {
    ContentBlock contentBlock = checkAuthorizeContent(contentId, blockId);
    if (updateContentBlockRequest.getIsFree() != null) {
      contentBlock.setIsFree(updateContentBlockRequest.getIsFree());
    }
    if (updateContentBlockRequest.getTextContent() != null) {
      contentBlock.setTextContent(updateContentBlockRequest.getTextContent());
    }
    if (updateContentBlockRequest.getProperties() != null) {
      contentBlock.setProperties(updateContentBlockRequest.getProperties());
    }
    if (updateContentBlockRequest.getType() != null) {
      contentBlock.setType(updateContentBlockRequest.getType());
    }
    contentBlockRepository.save(contentBlock);
    return contentBlock;
  }

  @Override
  @Transactional
  public void deleteContentBlock(Long contentId, Long blockId) {
    ContentBlock contentBlock = checkAuthorizeContent(contentId, blockId);
    Long parentBlockId=null;
    if (contentBlock.getParentBlock() != null) {
      parentBlockId = contentBlock.getParentBlock().getId();
    }
    adjustPositionBlock(contentId, parentBlockId,contentBlock.getPosition(),true);

    contentBlockRepository.deleteById(blockId);
  }

  @Override
  public ContentBlock getContentBlock(Long contentId, Long blockId) {
    return checkAuthorizeContent(contentId, blockId);
  }

  @Override
  public ContentBlock moveContentBlock(Long contentId, Long blockId,
      MoveBlockRequest moveBlockRequest) {
    return null;
  }

  public ContentBlock checkAuthorizeContent(Long contentId, Long blockId) {
    Content content = contentRepository.findById(contentId)
        .orElseThrow(() -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND));
    if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
    }
    return contentBlockRepository.findById(blockId)
        .orElseThrow(() -> new DevSharingException(ExceptionEnum.BLOCK_NOT_FOUND));
  }

  public void adjustPositionBlock(Long contentId, Long parentBlockId, Integer positionBlock,
      Boolean isDeleted) {
    List<ContentBlock> contentBlockList;
    if (parentBlockId == null) {
      contentBlockList = contentBlockRepository.findByContentIdAndPositionIsNotNullOrderByPositionAsc(
          contentId);
    } else {
      contentBlockList = contentBlockRepository.findByParentBlockIdAndPositionIsNotNullOrderByPosition(
          parentBlockId);
    }
    contentBlockList.stream().filter(contentBlock -> contentBlock.getPosition() >= positionBlock)
        .forEach(contentBlock -> {
          contentBlock.setPosition(
              isDeleted ? contentBlock.getPosition() - 1 : contentBlock.getPosition() + 1);
        });
    contentBlockRepository.saveAll(contentBlockList);
  }
}
