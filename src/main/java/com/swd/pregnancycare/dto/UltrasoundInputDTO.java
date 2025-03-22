package com.swd.pregnancycare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UltrasoundInputDTO {
    public LocalDate ultrasoundDate;
    public int gestationalWeeks;
    public int gestationalDays;
}
