package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "fetus")
@Data
public class FetusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private LocalDateTime dueDate;
    private String gender;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;


}
