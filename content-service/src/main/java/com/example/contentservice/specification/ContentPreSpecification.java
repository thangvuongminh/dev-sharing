package com.example.contentservice.specification;

import com.example.contentservice.dto.request.ContentSearchRequest;
import com.example.contentservice.entity.Content;
import com.example.contentservice.entity.ContentStatus;
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
  // Filter no status
  public static Specification<Content> withFiltersUnLimited(ContentSearchRequest contentSearchRequest) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (contentSearchRequest.getKeyword() != null && !contentSearchRequest.getKeyword().isEmpty()) {
        String expression = "%" + contentSearchRequest.getKeyword() + "%";
        Predicate predicateTitle = criteriaBuilder.like(root.get("title"), expression);
        Predicate predicateDesc = criteriaBuilder.like(root.get("description"), expression);
        predicates.add(criteriaBuilder.and(predicateTitle,predicateDesc));
      }
      if (contentSearchRequest.getCategoryId() != null) {
        Predicate predicateCategory = criteriaBuilder.equal(root.get("categoryId"), contentSearchRequest.getCategoryId());
        predicates.add(predicateCategory);
      }
      if (contentSearchRequest.getStatus() != null) {
        Predicate predicateStatus = criteriaBuilder.equal(root.get("status"), contentSearchRequest.getStatus());
        predicates.add(predicateStatus);
      }
      if(contentSearchRequest.getMinPrice()!=null){
        Predicate predicateMinPrice = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), contentSearchRequest.getMinPrice());
        predicates.add(predicateMinPrice);
      }
      if(contentSearchRequest.getMaxPrice()!=null){
        Predicate predicateMaxPrice= criteriaBuilder.lessThanOrEqualTo(root.get("price"), contentSearchRequest.getMaxPrice());
      }
      if(contentSearchRequest.getMinViewCount()!=null){
        Predicate  predicateMinView = criteriaBuilder.equal(root.get("viewCount"), contentSearchRequest.getMinViewCount());
        predicates.add(predicateMinView);
      }
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
  public static Specification<Content> withFiltersStatus(ContentSearchRequest contentSearchRequest,Boolean filterByAuthor) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      if (!filterByAuthor) {
        return criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), ContentStatus.PUBLISHED));
      }
      if(contentSearchRequest.getStatus()!=null ){
        return criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), contentSearchRequest.getStatus()));
      }
      return null;
    };
  }
}
