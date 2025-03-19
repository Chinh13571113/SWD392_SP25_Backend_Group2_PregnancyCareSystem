package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AppointmentDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentServices {
    void makeAppointment(AppointmentDTO appointment);
    void deleteAppointment(int id);
    List<AppointmentDTO> getAllAppointment();
    void updateAppointment(AppointmentDTO appointmentDTO);
}
