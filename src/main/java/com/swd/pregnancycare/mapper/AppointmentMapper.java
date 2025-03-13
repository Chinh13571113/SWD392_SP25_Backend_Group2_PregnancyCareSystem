package com.swd.pregnancycare.mapper;

import com.swd.pregnancycare.dto.AppointmentDTO;
import com.swd.pregnancycare.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(source = "fetus.id", target = "idFetus")


    AppointmentDTO toAppointmentDTO(AppointmentEntity appointmentEntity);
    List<AppointmentDTO> toListAppointmentDTO(List<AppointmentEntity> appointmentEntity);
}
