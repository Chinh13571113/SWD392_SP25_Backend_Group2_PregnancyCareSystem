package com.swd.pregnancycare.response;

import com.swd.pregnancycare.dto.ArticleSectionDTO;
import com.swd.pregnancycare.dto.BlogCategoryDTO;
import com.swd.pregnancycare.dto.UserDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
  int id;
  String title;
  String description;
  LocalDateTime datePublish;
  boolean status;
  String slug;
  UserDTO user;
  BlogCategoryDTO blogCategory;
  List<ArticleSectionDTO> articleSections;
}
