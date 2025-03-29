package com.swd.pregnancycare.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageDTO {
   String name ;
   String description ;
   double price ;
}
