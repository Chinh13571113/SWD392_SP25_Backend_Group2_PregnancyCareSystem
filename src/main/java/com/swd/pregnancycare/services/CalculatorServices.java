package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.ConceptionInputDTO;
import com.swd.pregnancycare.dto.IVFInputDTO;
import com.swd.pregnancycare.dto.LastPeriodInputDTO;
import com.swd.pregnancycare.dto.UltrasoundInputDTO;

import java.time.LocalDate;


public interface CalculatorServices {
    String getPeriodMethod(LastPeriodInputDTO periodInputDTO);
    String getConceptionDate(ConceptionInputDTO conceptionInputDTO);
    String getIVF(IVFInputDTO ivfInputDTO);
    String getUltraSound(UltrasoundInputDTO ultrasoundInputDTO);

    String getFromDueDate(LocalDate dueDate);
}
