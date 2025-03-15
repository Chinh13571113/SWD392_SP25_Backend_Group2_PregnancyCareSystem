package com.swd.pregnancycare.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRequest {
   String title;
   String description;
   int groupId;
   int blogCategoryId;
}
