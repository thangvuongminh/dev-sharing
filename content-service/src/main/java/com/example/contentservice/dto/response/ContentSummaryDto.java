package com.example.contentservice.dto.response;

import com.example.contentservice.entity.ContentLevel;
import com.example.contentservice.entity.ContentStatus;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentSummaryDto {
  Long id;
  String title;
  String description;
  Long creatorId;
  CategoryDto category;
  ContentStatus status;
  ContentLevel level;
  BigDecimal price;
  String thumbnail;
  Long viewCount;
  Long purchaseCount;
  Instant publishedAt;
  Instant createdAt;
}
