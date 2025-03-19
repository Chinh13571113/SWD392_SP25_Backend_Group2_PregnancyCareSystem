package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AppointmentDTO;
import com.swd.pregnancycare.entity.AppointmentEntity;
import com.swd.pregnancycare.entity.FetusEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.AppointmentMapper;
import com.swd.pregnancycare.repository.AppointmentRepo;
import com.swd.pregnancycare.repository.FetusRepo;
import com.swd.pregnancycare.repository.UserRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AppointmentServicesImp implements AppointmentServices {
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
    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public void makeAppointment(AppointmentDTO appointment) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UserEntity userEntity =userRepo.findByEmail(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        FetusEntity fetusEntity;

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
        List<AppointmentEntity> appointmentDTOList = appointmentRepo.findByUsersId(user.getId());

        return AppointmentMapper.INSTANCE.toListAppointmentDTO(appointmentDTOList);
    }

    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public void updateAppointment(int id, String newEvent) {
        AppointmentEntity appointmentEntity = appointmentRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXIST));
        appointmentEntity.setEvent(newEvent);
        appointmentRepo.save(appointmentEntity);
    }
}
