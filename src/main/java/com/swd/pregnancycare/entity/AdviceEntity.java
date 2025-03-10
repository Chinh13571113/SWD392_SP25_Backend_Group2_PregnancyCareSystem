package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "advices")
@Data
public class AdviceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private boolean status;

  @Column(name = "answer")
  private String answer;
}
