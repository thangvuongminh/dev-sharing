package com.example.contentservice.specification;

import com.example.contentservice.dto.request.ContentSearchRequest;
import com.example.contentservice.entity.Content;
import com.example.contentservice.utils.UserContentExtractor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class ContentPreSpecification {

  public static Specification<Content> withFiltersByAuthor(ContentSearchRequest contentSearchRequest) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("creatorId"), UserContentExtractor.getUserId());
    };
  }
}
