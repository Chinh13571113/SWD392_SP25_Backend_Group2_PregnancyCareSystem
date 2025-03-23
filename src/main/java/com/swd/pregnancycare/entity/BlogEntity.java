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
  @Column(name = "is_delete")
  private Boolean deleted;
  private String slug;



  // User
  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;


  //Group
  @ManyToOne
  @JoinColumn(name = "id_group")
  private GroupEntity group;


  // Blog categories
  @ManyToOne
  @JoinColumn(name = "id_category")
  private BlogCategoryEntity blogCategory;


  // Blog comments
  @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BlogCommentEntity> blogComments;


  // Article Section
  @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ArticleSectionEntity> articleSections;


  public boolean getStatus() {
    return status;
  }

}
