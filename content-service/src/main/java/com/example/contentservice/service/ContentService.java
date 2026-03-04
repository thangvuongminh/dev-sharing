package com.example.contentservice.service;

import com.example.contentservice.dto.request.ContentSearchRequest;
import com.example.contentservice.dto.request.CreateContentRequest;
import com.example.contentservice.dto.request.UpdateContentRequest;
import com.example.contentservice.dto.response.ContentDto;
import com.example.contentservice.dto.response.ContentSummaryDto;
import com.example.contentservice.entity.Content;
import org.springframework.data.domain.Page;

public interface ContentService {

  //  CRUD CONTENT
  public Content createContent(CreateContentRequest createContentRequest);

  public Content getContentById(Long id);

  public Content updateContent(Long id, UpdateContentRequest updateContentRequest);

  public void deleteContentById(Long id);

  // search and filter pagination
  public Page<Content> searchContentByAuthor(ContentSearchRequest contentSearchRequest);
  public Page<Content> searchContentAnyUsers(ContentSearchRequest contentSearchRequest);

  // content management
  public Content publishContent(Long contentId) ;
  public Content archiveContent(Long contentId);
  public Content submitForReview(Long contentId);

  // Tracking (called by other services)
  public void incrementViewCount(Long contentId);
  public void incrementPurchaseCount(Long contentId);


}
