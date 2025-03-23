package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "standardwho")
public class WhoStandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "gestation_week")
    int fetusWeek;
    @Column(name = "standard_weight")
    BigDecimal weight;
    @Column(name = "standard_height")
    BigDecimal height;
}
