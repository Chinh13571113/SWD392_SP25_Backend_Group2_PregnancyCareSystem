package com.swd.pregnancycare.mapper;

import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.request.PackageRequest;
import com.swd.pregnancycare.response.PackageResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PackageMapper {
  PackageEntity toEntity(PackageRequest request);
  PackageResponse toResponse(PackageEntity entity);
  List<PackageResponse> toResponseList(List<PackageEntity> entities);
}
