package com.swd.pregnancycare.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogDTO {
  private int id;
  private String title;
  private String description;
  private LocalDateTime datePublish;
  private boolean status;
  private int userId;
}
