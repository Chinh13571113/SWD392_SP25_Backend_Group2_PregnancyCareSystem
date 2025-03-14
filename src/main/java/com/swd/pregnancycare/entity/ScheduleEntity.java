package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "schedule")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "notification")
    String notify;
    @Column(name = "date_remind")
    LocalDateTime dateRemind;
    @Column(name = "is_notified")
    boolean isNotice;
    @Column(name = "type")
    String type;
    @ManyToOne
    @JoinColumn(name = "id_appointment")
    AppointmentEntity appointment;

}
