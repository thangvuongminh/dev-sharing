package com.example.contentservice.dto.response;

import com.example.contentservice.entity.ContentLevel;
import com.example.contentservice.entity.ContentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentDto {
    String title;
    String description;
    ContentLevel contentLevel;
    Long creatorId;
    CategoryDto category;
    BigDecimal price;
    String thumbnail;
    ContentStatus status;
    ContentLevel level;
    Long viewCount;
    Long purchaseCount;
    Instant publishedAt;
    Instant createdAt;
    Instant updatedAt;
    List<ContentBlockDto> blocks;
}
