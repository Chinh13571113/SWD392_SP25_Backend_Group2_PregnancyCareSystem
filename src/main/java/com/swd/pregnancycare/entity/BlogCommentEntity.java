package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "blog_comment")
@Data
public class BlogCommentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "description")
  private String description;
  @Column(name = "datePublish")
  private LocalDateTime datePublish;
  // Blog category
  @ManyToOne
  @JoinColumn(name = "id_category")
  private BlogCategoryEntity blogCategory;
  // User
  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;
  // Blog
  @ManyToOne
  @JoinColumn(name = "id_blog")
  private BlogEntity blog;
}
