package com.swd.pregnancycare.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdviceDTO {
  int id;
  String title;
  String description;
  boolean status;
  String answer;
  FetusDTO fetus;
}
