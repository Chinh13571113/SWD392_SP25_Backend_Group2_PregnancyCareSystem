package com.swd.pregnancycare.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FetalHeightEntity {
    private int week;
    private double lowerBound;
    private double average;
    private double upperBound;
    private String unit;
}
