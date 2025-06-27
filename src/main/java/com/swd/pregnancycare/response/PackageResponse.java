package com.swd.pregnancycare.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageResponse {
  int id;
  String name;
  double price;
  String description;
}
