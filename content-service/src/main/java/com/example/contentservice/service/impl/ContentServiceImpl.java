package com.example.contentservice.service.impl;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.example.contentservice.dto.request.ContentSearchRequest;
import com.example.contentservice.dto.request.CreateContentRequest;
import com.example.contentservice.dto.request.UpdateContentRequest;
import com.example.contentservice.dto.response.ContentDto;
import com.example.contentservice.dto.response.ContentSummaryDto;
import com.example.contentservice.entity.Category;
import com.example.contentservice.entity.Content;
import com.example.contentservice.entity.ContentStatus;
import com.example.contentservice.exception.ExceptionEnum;
import com.example.contentservice.mapper.ContentMapper;
import com.example.contentservice.repository.CategoryRepository;
import com.example.contentservice.repository.ContentRepository;
import com.example.contentservice.service.CategoryService;
import com.example.contentservice.service.ContentService;
import com.example.contentservice.specification.ContentPreSpecification;
import com.example.contentservice.specification.PageableSorting;
import com.example.contentservice.utils.UserContentExtractor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentServiceImpl implements ContentService {

  ContentRepository contentRepository;
  CategoryService categoryService;
  ContentMapper contentMapper;

  @Override
  @Transactional
  public Content createContent(CreateContentRequest createContentRequest) {
    Category category = categoryService.getCategoryById(createContentRequest.getCategoryId());
    Content content = Content.builder()
        .creatorId(UserContentExtractor.getUserId())
        .description(createContentRequest.getDescription())
        .price(createContentRequest.getPrice())
        .title(createContentRequest.getTitle())
        .thumbnail(createContentRequest.getThumbnail())
        .categoryId(category.getId())
        .level(createContentRequest.getContentLevel())
        .status(ContentStatus.DRAFT)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
    contentRepository.save(content);
    return content;
  }

  @Override
  @Transactional
  public Content updateContent(Long id, UpdateContentRequest updateContentRequest) {
    Content content = contentRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
    }
    if (updateContentRequest.getTitle() != null) {
      content.setTitle(updateContentRequest.getTitle());
    }
    if (updateContentRequest.getThumbnail() != null) {
      content.setThumbnail(updateContentRequest.getThumbnail());
    }
    if (updateContentRequest.getDescription() != null) {
      content.setDescription(updateContentRequest.getDescription());
    }
    if (updateContentRequest.getPrice() != null) {
      content.setPrice(updateContentRequest.getPrice());
    }
    if (updateContentRequest.getContentLevel() != null) {
      content.setLevel(updateContentRequest.getContentLevel());
    }
    if (updateContentRequest.getCategoryId() != null) {
      Category category = categoryService.getCategoryById(updateContentRequest.getCategoryId());
      content.setCategoryId(category.getId());
    }
    content.setUpdatedAt(Instant.now());
    contentRepository.save(content);
    return content;
  }

  @Override
  @Transactional(readOnly = true)
  public Content getContentById(Long id) {
    Content content = contentRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    if (!(content.getStatus() == ContentStatus.PUBLISHED)) {
      if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
        throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
      }
    }
    return content;
  }

  @Transactional
  public void deleteContentById(Long id) {
    Content content = contentRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
    }
    contentRepository.deleteById(id);
  }

  @Override
  public Page<Content> searchContentByAuthor(ContentSearchRequest contentSearchRequest) {
    Pageable pageable = PageableSorting.getPageable(contentSearchRequest.getSortBy(),
        contentSearchRequest.getSortDirection(), contentSearchRequest.getPage(),
        contentSearchRequest.getSize());
    Specification<Content> spec = Specification.allOf(
        ContentPreSpecification.withFiltersByAuthor(contentSearchRequest),
        ContentPreSpecification.withFiltersUnLimited(contentSearchRequest),
        ContentPreSpecification.withFiltersStatus(contentSearchRequest, true));
    return contentRepository.findAll(
        spec, pageable);
  }

  @Override
  public Page<Content> searchContentAnyUsers(ContentSearchRequest contentSearchRequest) {
    Pageable pageable = PageableSorting.getPageable(contentSearchRequest.getSortBy(),
        contentSearchRequest.getSortDirection(), contentSearchRequest.getPage(),
        contentSearchRequest.getSize());
    Specification<Content> spec = Specification.allOf(
        ContentPreSpecification.withFiltersUnLimited(contentSearchRequest),
        ContentPreSpecification.withFiltersStatus(contentSearchRequest, false));
    return contentRepository.findAll(
        spec, pageable);
  }

  @Override
  @Transactional
  public Content publishContent(Long id) {
    Content content = contentRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
    }
    if (content.getStatus() != ContentStatus.APPROVED   ) {
      throw new DevSharingException(ExceptionEnum.CONTENT_NO_REVIEW);
    }
    content.setUpdatedAt(Instant.now());
    content.setPublishedAt(Instant.now());
    content.setStatus(ContentStatus.PUBLISHED);
    contentRepository.save(content);
    return content;
  }
  @Override
  @Transactional
  public Content archiveContent(Long id) {
    Content content = contentRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
    }
    content.setUpdatedAt(Instant.now());
    content.setPublishedAt(Instant.now());
    content.setStatus(ContentStatus.ARCHIVED);
    contentRepository.save(content);
    return content;
  }
  @Override
  @Transactional
  public Content submitForReview(Long id) {
    Content content = contentRepository.findById(id).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    if (!content.getCreatorId().equals(UserContentExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.UNAUTHORIZE_CONTENT_ACCESS);
    }
    if (content.getStatus() == ContentStatus.APPROVED ||  content.getStatus() == ContentStatus.REJECTED) {
      throw new DevSharingException(ExceptionEnum.CONTENT_NO_REVIEW);
    }
    content.setUpdatedAt(Instant.now());
    content.setPublishedAt(Instant.now());
    content.setStatus(ContentStatus.PENDING_REVIEW);
    contentRepository.save(content);
    return content;
  }

  @Override
  @Transactional
  public void incrementViewCount(Long contentId) {
    Content content = contentRepository.findById(contentId).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    content.setViewCount(content.getViewCount() + 1);
    contentRepository.save(content);
  }

  @Override
  @Transactional
  public void incrementPurchaseCount(Long contentId) {
    Content content = contentRepository.findById(contentId).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.CONTENT_NOT_FOUND)
    );
    content.setPurchaseCount(content.getPurchaseCount() + 1);
    contentRepository.save(content);
  }

}
