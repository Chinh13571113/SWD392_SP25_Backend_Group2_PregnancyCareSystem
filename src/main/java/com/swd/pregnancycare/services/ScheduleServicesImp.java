package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.DataMailDTO;
import com.swd.pregnancycare.dto.ScheduleDTO;
import com.swd.pregnancycare.entity.AppointmentEntity;
import com.swd.pregnancycare.entity.ScheduleEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.ScheduleMapper;
import com.swd.pregnancycare.repository.AppointmentRepo;
import com.swd.pregnancycare.repository.ScheduleRepo;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServicesImp implements ScheduleServices{
    private static final Logger log = LoggerFactory.getLogger(ScheduleServicesImp.class);
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    MailServices mailServices;
    @PreAuthorize("hasRole('MEMBER')")
    @Override
    public void createReminder(ScheduleDTO scheduleDTO) {
        AppointmentEntity appointmentEntity = appointmentRepo.findById(scheduleDTO.getAppointmentId()).orElseThrow(()->new AppException(ErrorCode.APPOINTMENT_NOT_EXIST));

        LocalDateTime upperBound = appointmentEntity.getDateIssue().plusHours(1);

        if(scheduleDTO.getDateRemind().isBefore(appointmentEntity.getDateIssue()) || scheduleDTO.getDateRemind().isAfter(upperBound)) throw new AppException(ErrorCode.SCHEDULE_EXISTED);
        boolean exists = scheduleRepo.findExistingSchedule(scheduleDTO.getAppointmentId(), scheduleDTO.getDateRemind()).isPresent();
        if (exists) {
            throw new AppException(ErrorCode.SCHEDULE_EXISTED); // Lỗi mới để báo trùng lịch
        }
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .notify(scheduleDTO.getNotify())
                .appointment(appointmentEntity)
                .dateRemind(scheduleDTO.getDateRemind())
                .isNotice(false)
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
    public void updateReminder(ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity= scheduleRepo.findById(scheduleDTO.getId()).orElseThrow(()-> new AppException(ErrorCode.SCHEDULE_NOT_EXIST));
        AppointmentEntity appointmentEntity = appointmentRepo.findById(scheduleDTO.getAppointmentId()).orElseThrow(()-> new AppException(ErrorCode.APPOINTMENT_NOT_EXIST));
        scheduleEntity.setAppointment(appointmentEntity);
        scheduleEntity.setDateRemind(appointmentEntity.getDateIssue());
        scheduleEntity.setNotice(false);
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
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void sendReminderEmailOnStartup() throws MessagingException {
        System.out.println("🚀 Server started! Checking for reminders...");
        sendReminderEmail();
    }
    @Scheduled(cron = "0 * * * * ?") // Chạy lúc 8h sáng hàng ngày
    @Transactional
    @Override
    public void sendReminderEmail() throws MessagingException {
        List<ScheduleEntity> schedules = scheduleRepo.findSchedulesToNotify();
        for(ScheduleEntity schedule:schedules){
            String email = schedule.getAppointment().getUsers().getEmail();
            DataMailDTO dataMailDTO = getDataMailDTO(schedule, email);
            mailServices.sendHtmlMail(dataMailDTO,"fetusSchedule");
            schedule.setNotice(true);


        }
        scheduleRepo.saveAll(schedules);
    }

    private static DataMailDTO getDataMailDTO(ScheduleEntity schedule, String email) {
        String eventName = schedule.getAppointment().getEvent();
        String eventSubject = schedule.getNotify();
        LocalDateTime eventTime = schedule.getDateRemind();
        DataMailDTO dataMailDTO = new DataMailDTO();
        dataMailDTO.setTo(email);
        dataMailDTO.setSubject(schedule.getAppointment().getEvent());
        Map<String,Object> props = new HashMap<>();
        props.put("eventName", eventName);
        props.put("eventDate",eventTime);
        props.put("eventSubject",eventSubject);
        dataMailDTO.setProps(props);
        return dataMailDTO;
    }
}
