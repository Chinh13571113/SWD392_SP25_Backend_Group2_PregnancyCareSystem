package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_group")
@IdClass(UserGroupId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupEntity {

  @Column(name = "is_delete")
  private Boolean deleted;



  @Id
  @ManyToOne
  @JoinColumn(name = "id_user")
  private UserEntity user;



  @Id
  @ManyToOne
  @JoinColumn(name = "id_group")
  private GroupEntity group;
}
