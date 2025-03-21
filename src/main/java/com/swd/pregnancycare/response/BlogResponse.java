package com.swd.pregnancycare.response;

import com.swd.pregnancycare.dto.ArticleSectionDTO;
import com.swd.pregnancycare.dto.BlogCategoryDTO;
import com.swd.pregnancycare.dto.BlogCommentDTO;
import com.swd.pregnancycare.dto.UserDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogResponse {
  int id;
  String title;
  String description;
  LocalDateTime datePublish;
  boolean status;
  String slug;
  Boolean deleted;
  UserDTO user;
  BlogCategoryDTO blogCategory;
  List<BlogCommentDTO> blogComments;
  List<ArticleSectionDTO> articleSections;
}
