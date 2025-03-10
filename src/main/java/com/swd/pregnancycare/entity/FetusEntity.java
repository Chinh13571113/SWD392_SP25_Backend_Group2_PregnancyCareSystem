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
    @Column(name = "name")
    private String name;
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    @Column(name = "gender")
    private String gender;
    @Column(name = "status")
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;


}
