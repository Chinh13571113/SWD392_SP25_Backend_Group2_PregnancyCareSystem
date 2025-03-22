package com.swd.pregnancycare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastPeriodInputDTO {
    LocalDate lastPeriod;
    int cycleLength;
}
