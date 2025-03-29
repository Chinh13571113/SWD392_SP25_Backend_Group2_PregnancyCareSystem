package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name="userpackage")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int package_id ;
    @Column(name = "id_user")
    private int user_id ;
    private LocalDateTime dateBegin ;
    private LocalDateTime dateEnd ;

}
