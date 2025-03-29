package com.swd.pregnancycare.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleRequest {
   String title;
   String description;
   int blogCategoryId;
   List<ArticleSectionRequest> articleSections;
}
