package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentServices {
    void makeAppointment(AppointmentDTO appointment);
    List<AppointmentDTO> getAppointmentByFetusId(int fetusId);
    void deleteAppointment(int id);
    List<AppointmentDTO> getAllAppointment();
    void updateAppointment(int id,String event);
}
