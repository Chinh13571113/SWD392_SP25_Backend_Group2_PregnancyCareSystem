package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "`group`")
@Data
public class GroupEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "date")
  private LocalDateTime date;

  @OneToOne
  @JoinColumn(name = "id_owner", unique = true)
  private UserEntity user;

  @ManyToMany
  @JoinTable(
          name = "user_group",
          joinColumns = @JoinColumn(name = "id_group"),
          inverseJoinColumns = @JoinColumn(name = "id_user")
  )
  private List<UserEntity> users = new ArrayList<>();

  //Blog
  @OneToMany(mappedBy = "group")
  private List<BlogEntity> blogs;

}
