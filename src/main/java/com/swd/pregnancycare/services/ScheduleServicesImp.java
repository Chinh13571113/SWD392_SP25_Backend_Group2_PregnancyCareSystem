package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.ScheduleDTO;
import com.swd.pregnancycare.entity.AppointmentEntity;
import com.swd.pregnancycare.entity.ScheduleEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.ScheduleMapper;
import com.swd.pregnancycare.repository.AppointmentRepo;
import com.swd.pregnancycare.repository.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScheduleServicesImp implements ScheduleServices{
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @PreAuthorize("hasRole('MEMBER')")
    @Override
    public void createReminder(ScheduleDTO scheduleDTO) {
        AppointmentEntity appointmentEntity = appointmentRepo.findById(scheduleDTO.getAppointmentId()).orElseThrow(()->new AppException(ErrorCode.APPOINTMENT_NOT_EXIST));
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .notify(scheduleDTO.getNotify())
                .appointment(appointmentEntity)
                .dateRemind(appointmentEntity.getDateIssue())
                .isNotice(scheduleDTO.isNotice())
                .type(scheduleDTO.getType()).build();

        scheduleRepo.save(scheduleEntity);


    }

    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public List<ScheduleDTO> getReminderByAppointmentId(int id) {
        List<ScheduleEntity> scheduleEntityList = scheduleRepo.findByAppointmentId(id);
        if(scheduleEntityList.isEmpty()) throw new AppException(ErrorCode.SCHEDULE_NOT_EXIST);
        return ScheduleMapper.INSTANCE.toListScheduleDTO(scheduleEntityList);
    }

    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public void updateReminder(int id, ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity= scheduleRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.SCHEDULE_NOT_EXIST));
        AppointmentEntity appointmentEntity = appointmentRepo.findById(scheduleDTO.getAppointmentId()).orElseThrow(()-> new AppException(ErrorCode.APPOINTMENT_NOT_EXIST));
        scheduleEntity.setAppointment(appointmentEntity);
        scheduleEntity.setDateRemind(appointmentEntity.getDateIssue());
        scheduleEntity.setNotice(scheduleDTO.isNotice());
        scheduleEntity.setNotify(scheduleDTO.getNotify());
        scheduleEntity.setType(scheduleDTO.getType());
        scheduleRepo.save(scheduleEntity);
    }

    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public void deleteReminder(int id) {
        scheduleRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.SCHEDULE_NOT_EXIST));
        scheduleRepo.deleteById(id);
    }

    @Override
    public void sendReminderEmail() {

    }
}
