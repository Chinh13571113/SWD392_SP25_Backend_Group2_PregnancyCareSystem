package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FetusEntity> fetus;



    // Blogs
    @OneToMany(mappedBy ="user")
    private List<BlogEntity> blogs;
    // Blog comments
    @OneToMany(mappedBy = "user")
    private List<BlogCommentEntity> blogComments;

    //Group
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private GroupEntity group;
    @ManyToMany(mappedBy = "users")
    private List<GroupEntity> groups;

}
