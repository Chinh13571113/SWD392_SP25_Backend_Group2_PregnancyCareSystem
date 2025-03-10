package com.swd.pregnancycare.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupDTO {
  private int id;
  private String name;
  private String description;
  private LocalDateTime date;
  private int userId;
}
