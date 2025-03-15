package com.swd.pregnancycare.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDTO {
   int id;
   String name;
   String description;
   LocalDateTime date;
   Boolean deleted;
   UserDTO owner;
   List<UserDTO> users;
}
