package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "blog")
@Data
public class BlogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "title")
  private String title;
  @Column(name = "description")
  private String description;
  @Column(name = "datePublish")
  private LocalDateTime datePublish;
  @Column(name = "status")
  private boolean status;
  // Blog comments
  @OneToMany(mappedBy = "blog")
  private List<BlogCommentEntity> blogComments;
  // Blog categories
  @OneToMany(mappedBy = "blog")
  private List<BlogCategoryEntity> blogCategories;
  // User
  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;

  public boolean getStatus() {
    return status;
  }

}
