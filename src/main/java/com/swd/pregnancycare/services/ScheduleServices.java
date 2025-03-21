package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.ScheduleDTO;
import jakarta.mail.MessagingException;

import java.util.List;

public interface ScheduleServices {
    void createReminder(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getReminderByAppointmentId(int id);
    void updateReminder(ScheduleDTO scheduleDTO);
    void deleteReminder(int id);
    void sendReminderEmail() throws MessagingException;

}
