package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "blog_category")
@Data
public class BlogCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "description")
  private String description;
  @Column(name = "datePublish")
  private LocalDateTime datePublish;
  // Blog comments
  @OneToMany(mappedBy = "blogCategory")
  private List<BlogCommentEntity> blogComments;
  // User
  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;
  // Blog
  @ManyToOne
  @JoinColumn(name = "id_blog")
  private BlogEntity blog;
}
