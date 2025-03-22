package com.swd.pregnancycare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IVFInputDTO {
    LocalDate conceptionDate;
    int ivdDate;
}
