package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.ScheduleDTO;
import com.swd.pregnancycare.repository.AppointmentRepo;
import com.swd.pregnancycare.repository.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScheduleServicesImp implements ScheduleServices{
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Override
    public void createReminder(ScheduleDTO scheduleDTO) {


    }

    @Override
    public List<ScheduleDTO> getReminderByAppointmentId(int id) {
        return List.of();
    }

    @Override
    public void updateReminder(int id) {

    }

    @Override
    public void deleteReminder(int id) {

    }

    @Override
    public void sendReminderEmail() {

    }
}
