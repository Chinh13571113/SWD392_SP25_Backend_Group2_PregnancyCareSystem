package com.swd.pregnancycare.response;


import com.swd.pregnancycare.dto.BlogCategoryDTO;
import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.dto.UserDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdviceResponse {
  int id;
  String title;
  String description;
  LocalDateTime datePublish;
  Boolean status;
  String answer;
  LocalDateTime answerDate;
  UserDTO member;
  UserDTO expert;
  FetusDTO fetus;
  BlogCategoryDTO blogCategory;
}
