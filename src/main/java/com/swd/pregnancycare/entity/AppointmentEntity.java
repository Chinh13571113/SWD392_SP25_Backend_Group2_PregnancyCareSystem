package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "appointment")
@Table(
        name = "appointment",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"date", "id_user"})
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String event;
    @Column(name = "date")
    LocalDateTime dateIssue;
    @ManyToOne
    @JoinColumn(name = "id_user")
    UserEntity users;
    @OneToMany(mappedBy = "appointment")
    List<ScheduleEntity> schedules;
}
