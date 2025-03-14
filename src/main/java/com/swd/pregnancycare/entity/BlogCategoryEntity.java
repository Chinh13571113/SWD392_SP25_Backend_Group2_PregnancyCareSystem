package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "blog_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String slug;
  private String description;
  @Column(name = "datePublish")
  private LocalDateTime datePublish;
  @Column(name = "is_delete")
  private Boolean deleted;


  // Blog
  @OneToMany(mappedBy = "blogCategory")
  private List<BlogEntity> blogs;
}
