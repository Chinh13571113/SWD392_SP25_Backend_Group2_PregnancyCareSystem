package com.swd.pregnancycare.response;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.dto.UserDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupResponse {
   int id;
   String name;
   String description;
   LocalDateTime date;
   Boolean deleted;
   UserDTO owner;
   List<UserDTO> users;
   List<BlogDTO> blogs;
}
