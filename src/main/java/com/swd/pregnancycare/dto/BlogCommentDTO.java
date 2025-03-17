package com.swd.pregnancycare.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogCommentDTO {
  int id;
  String description;
  LocalDateTime datePublish;
  UserDTO user;
  BlogDTO blog;
}
