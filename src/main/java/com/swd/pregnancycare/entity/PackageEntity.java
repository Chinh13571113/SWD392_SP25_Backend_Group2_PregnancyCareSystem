package com.swd.pregnancycare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String name ;
    private String description ;
    private double price ;

}
