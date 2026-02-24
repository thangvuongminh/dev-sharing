package com.example.contentservice.dto.response;

import com.example.contentservice.entity.ContentLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    Long id;
    String name;
    String description;
    String slug;
}
