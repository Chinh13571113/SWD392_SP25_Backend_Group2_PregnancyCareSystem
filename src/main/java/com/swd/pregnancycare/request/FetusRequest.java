package com.swd.pregnancycare.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FetusRequest {
  private LocalDateTime dueDate;
  private String name;
  private String gender;
}
