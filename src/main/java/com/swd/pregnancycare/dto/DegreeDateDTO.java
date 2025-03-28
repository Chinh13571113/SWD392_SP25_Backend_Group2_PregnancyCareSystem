package com.swd.pregnancycare.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DegreeDateDTO {
  LocalDateTime dateBegin;
  LocalDateTime dateEnd;
}
