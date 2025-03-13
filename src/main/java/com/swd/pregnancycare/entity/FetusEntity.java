package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "fetus")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "id_user")
    private UserEntity user;
    @OneToMany(mappedBy = "fetus")
    private Set<FetusRecordEntity> fetuses;


    @OneToMany(mappedBy = "fetus")
    List<AppointmentEntity> appointmentList;


    // Advice
    @OneToMany(mappedBy = "fetus")
    private List<AdviceEntity> advices;
}
