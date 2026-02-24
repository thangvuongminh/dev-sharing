package com.example.contentservice.dto.response;

import com.example.contentservice.entity.BlockType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentBlockDto {
    Long id;
    Long contentId;
    Long parentBlockId;
    BlockType type;
    String textContent;
    String properties; // JSON string
    Integer position;
    Boolean isFree;
    Instant createdAt;
    Instant updatedAt;
    List<ContentBlockDto> children;
}
