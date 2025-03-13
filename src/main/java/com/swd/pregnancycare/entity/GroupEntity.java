package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "`group`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  private LocalDateTime date;
  @Column(name = "is_delete")
  private Boolean deleted;



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
