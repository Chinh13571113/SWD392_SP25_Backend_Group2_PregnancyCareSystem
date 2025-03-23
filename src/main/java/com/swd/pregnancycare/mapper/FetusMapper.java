package com.swd.pregnancycare.mapper;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.dto.FetusRecodDTO;
import com.swd.pregnancycare.entity.FetusEntity;
import com.swd.pregnancycare.entity.FetusRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FetusMapper {
    FetusMapper INSTANCE = Mappers.getMapper(FetusMapper.class);
    List<FetusDTO> toListFetusDTO(List<FetusEntity> fetusEntityList);
    @Mapping(source = "dateRecord", target = "dateRecord")
    List<FetusRecodDTO> toListFetusRecordDTO(List<FetusRecordEntity> fetusRecordEntityList);
}
