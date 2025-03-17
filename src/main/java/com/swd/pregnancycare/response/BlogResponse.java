package com.swd.pregnancycare.response;

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
  UserDTO user;
  List<BlogCommentDTO> blogComments;
}
