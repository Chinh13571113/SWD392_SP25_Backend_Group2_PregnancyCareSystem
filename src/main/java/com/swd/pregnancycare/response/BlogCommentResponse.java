package com.swd.pregnancycare.response;


import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.dto.UserDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogCommentResponse {
  int id;
  String description;
  LocalDateTime datePublish;
  UserDTO user;
  BlogDTO blog;
}
