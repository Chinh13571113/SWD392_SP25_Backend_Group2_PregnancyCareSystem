package com.swd.pregnancycare.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogDTO {
   int id;
   String title;
   String description;
   LocalDateTime datePublish;
   boolean status;
   Boolean deleted;
   String slug;
   UserDTO user;
   GroupDTO group;
   List<BlogCommentDTO> blogComments;
   BlogCategoryDTO blogCategory;
}
