package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AppointmentDTO;
import com.swd.pregnancycare.entity.AppointmentEntity;
import com.swd.pregnancycare.entity.ScheduleEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.AppointmentMapper;
import com.swd.pregnancycare.repository.AppointmentRepo;
import com.swd.pregnancycare.repository.FetusRepo;
import com.swd.pregnancycare.repository.ScheduleRepo;
import com.swd.pregnancycare.repository.UserRepo;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServicesImp implements AppointmentServices {
    private static final Logger log = LoggerFactory.getLogger(AppointmentServicesImp.class);
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FetusRepo fetusRepo;
    @Autowired
    private  LoginServices loginServices;
    @Autowired
    private Validator validator;
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public void makeAppointment(AppointmentDTO appointment) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UserEntity userEntity =userRepo.findByEmailAndStatusTrue(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        if(appointment.getDateIssue().isBefore(LocalDateTime.now(ZoneId.of("UTC")))) throw new AppException(ErrorCode.SCHEDULE_INVALID);

         if(appointmentRepo.existsByDateIssueAndUsers(appointment.getDateIssue(),userEntity))
             throw new AppException(ErrorCode.AVAILABLE_WRITER);


        AppointmentEntity appointmentEntity = AppointmentEntity.builder()

                .users(userEntity)
                .event(appointment.getEvent())
                .dateIssue(appointment.getDateIssue())
                .build();

        appointmentRepo.save(appointmentEntity);


    }



    @PreAuthorize("hasRole('MEMBER')")
    @Override
    public void deleteAppointment(int id) {
        Optional<AppointmentEntity> appointment = appointmentRepo.findById(id);
        if(appointment.isEmpty()) throw new AppException(ErrorCode.APPOINTMENT_NOT_EXIST);
        appointmentRepo.deleteById(id);

    }

    @Override
    public List<AppointmentDTO> getAllAppointment() {
        UserEntity user = loginServices.getUser();
        List<AppointmentEntity> appointmentEntityList = appointmentRepo.findByUsersId(user.getId());

        return AppointmentMapper.INSTANCE.toListAppointmentDTO(appointmentEntityList);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MEMBER')")
    public void updateAppointment(AppointmentDTO appointmentDTO) {
        AppointmentEntity appointmentEntity = appointmentRepo.findById(appointmentDTO.getId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXIST));
        if (appointmentDTO.getDateIssue().isBefore(LocalDateTime.now(ZoneId.of("UTC")))) throw new AppException(ErrorCode.SCHEDULE_INVALID);
        if (!appointmentEntity.getDateIssue().toLocalDate().isEqual(appointmentDTO.getDateIssue().toLocalDate())) throw new AppException(ErrorCode.AVAILABLE_WRITER);

        appointmentEntity.setEvent(appointmentDTO.getEvent());
        appointmentEntity.setDateIssue(appointmentDTO.getDateIssue());
        appointmentRepo.save(appointmentEntity);
        scheduleRepo.deleteByAppointmentId(appointmentDTO.getId());


    }
}
