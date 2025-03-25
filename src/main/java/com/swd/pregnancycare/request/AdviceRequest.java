package com.swd.pregnancycare.request;

import com.swd.pregnancycare.dto.FetusDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdviceRequest {
  String title;
  String description;
  int fetusId;
  int categoryId;
}
