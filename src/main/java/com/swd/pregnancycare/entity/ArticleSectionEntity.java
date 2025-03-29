package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "article_sections")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSectionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "section_title")
  private String sectionTitle;
  private String description;
  private String anchor;
  @Column(name = "display_order")
  private int displayOrder;

  @ManyToOne
  @JoinColumn(name = "id_article")
  private BlogEntity blog;
}
