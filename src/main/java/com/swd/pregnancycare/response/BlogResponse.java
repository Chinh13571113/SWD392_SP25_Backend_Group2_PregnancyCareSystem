package com.swd.pregnancycare.response;

import com.swd.pregnancycare.dto.UserDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogResponse {
  int id;
  String titlle;
  String description;
  LocalDateTime datePublish;
  boolean status;
  UserDTO user;
}
