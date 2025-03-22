package com.swd.pregnancycare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PregnancyPeriodEntity {
    private int startWeek ;
    private int endWeek ;
    private String decription ;
    private int trimester ;
    private LocalDate suggestDate ;
}
