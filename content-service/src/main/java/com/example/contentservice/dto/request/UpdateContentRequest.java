package com.example.contentservice.dto.request;

import com.example.contentservice.entity.ContentLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateContentRequest {

  String title;
  String description;
  ContentLevel contentLevel;
  BigDecimal price;
  String thumbnail;
  Long  categoryId;
}
