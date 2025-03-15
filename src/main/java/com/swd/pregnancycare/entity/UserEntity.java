package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Email(message = "Email không hợp lệ, vui lòng nhập đúng định dạng.")
    @NotBlank(message = "Email không được để trống.")
    @Size(max = 255, message = "Email không được dài quá 255 ký tự.")
    @Column(name = "email")
    private String email;
    @Size(min = 8,message = "INVALID_PASSWORD")
    @Column(name = "password")
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "user")
    private List<FetusEntity> fetus;



    // Blogs
    @OneToMany(mappedBy ="user")
    private List<BlogEntity> blogs;



    // Blog comments
    @OneToMany(mappedBy = "user")
    private List<BlogCommentEntity> blogComments;



    // Owner of Group
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private GroupEntity group;



    // Group List
    @OneToMany(mappedBy = "user")
    private List<UserGroupEntity> groups;



    @OneToMany(mappedBy = "users")
    private List<AppointmentEntity> appointments;
}
