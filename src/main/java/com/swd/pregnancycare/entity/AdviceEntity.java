package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
  private LocalDateTime datePublish;
  private boolean status;
  private String answer;
  private LocalDateTime answerDate;



  @ManyToOne
  @JoinColumn(name = "fetus_id")
  private FetusEntity fetus;



  @ManyToOne
  @JoinColumn(name = "id_category")
  private BlogCategoryEntity category;


  // Expert
  @ManyToOne
  @JoinColumn(name = "id_expert")
  private UserEntity expert;
}
