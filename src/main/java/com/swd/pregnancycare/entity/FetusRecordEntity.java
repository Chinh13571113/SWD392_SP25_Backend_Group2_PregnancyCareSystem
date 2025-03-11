package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity(name = "fetus_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FetusRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    BigDecimal weight;
    BigDecimal height;
    @ManyToOne
    @JoinColumn(name = "id_fetus")
    FetusEntity fetus;

}
