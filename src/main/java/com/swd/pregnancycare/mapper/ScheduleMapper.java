package com.swd.pregnancycare.mapper;

import com.swd.pregnancycare.dto.ScheduleDTO;
import com.swd.pregnancycare.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
    @Mapping(source = "appointment.id", target = "appointmentId")
    ScheduleDTO toScheduleDTO(ScheduleEntity scheduleEntity);
    List<ScheduleDTO> toListScheduleDTO(List<ScheduleDTO> scheduleDTOList);
}
