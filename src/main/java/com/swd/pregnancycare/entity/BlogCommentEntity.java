package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "blog_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCommentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String description;
  @Column(name = "datePublish")
  private LocalDateTime datePublish;



  // User
  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;



  // Blog
  @ManyToOne
  @JoinColumn(name = "id_blog")
  private BlogEntity blog;





}
