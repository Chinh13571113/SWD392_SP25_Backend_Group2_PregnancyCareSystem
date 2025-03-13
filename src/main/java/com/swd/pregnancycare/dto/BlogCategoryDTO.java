package com.swd.pregnancycare.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogCategoryDTO {
  int id;
  String name;
  String slug;
  String description;
  LocalDateTime datePublish;
  Boolean deleted;
  List<BlogDTO> blogs;
}
