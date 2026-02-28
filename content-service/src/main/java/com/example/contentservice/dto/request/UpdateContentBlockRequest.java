package com.example.contentservice.dto.request;

import com.example.contentservice.entity.BlockType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateContentBlockRequest {
  BlockType type;
  String textContent;
  String properties;
  Boolean isFree;
}
