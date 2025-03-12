package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

//@Entity(name = "appointment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String event;
    LocalDateTime dateIssue;
    UserEntity users;
    FetusEntity fetus;
}
