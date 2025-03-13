package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String description;
  @Column(name = "datePublish")
  private LocalDateTime datePublish;
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
  //Group
  @ManyToOne
  @JoinColumn(name = "id_group")
  private GroupEntity group;

  public boolean getStatus() {
    return status;
  }

}
