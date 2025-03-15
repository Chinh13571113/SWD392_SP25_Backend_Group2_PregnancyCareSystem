package com.swd.pregnancycare.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupResponse {
   int id;
   String name;
   String description;
   UserResponse owner;
   LocalDateTime datePublish;
}
