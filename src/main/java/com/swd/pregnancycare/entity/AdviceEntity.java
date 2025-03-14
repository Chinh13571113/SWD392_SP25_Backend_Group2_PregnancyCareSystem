package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "advices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdviceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String description;
  private boolean status;
  private String answer;

  @ManyToOne
  @JoinColumn(name = "fetus_id")
  private FetusEntity fetus;
}
