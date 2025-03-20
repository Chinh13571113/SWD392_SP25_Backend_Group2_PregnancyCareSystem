package com.swd.pregnancycare.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleSectionDTO {
   int id;
   String sectionTitle;
   String description;
   String anchor;
   int displayOrder;
}
