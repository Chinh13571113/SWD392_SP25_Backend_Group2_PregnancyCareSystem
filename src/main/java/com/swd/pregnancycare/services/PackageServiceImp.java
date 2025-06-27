package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.PackageMapper;
import com.swd.pregnancycare.repository.PackageRepo;
import com.swd.pregnancycare.request.PackageRequest;
import com.swd.pregnancycare.response.PackageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImp implements PackageServices {

  @Autowired
  private PackageRepo packageRepo;
  @Autowired
  private PackageMapper packageMapper;

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public PackageResponse createPackage(PackageRequest request) {
    PackageEntity entity = packageMapper.toEntity(request);
    return packageMapper.toResponse(packageRepo.save(entity));
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public List<PackageResponse> getAllPackages() {
    return packageMapper.toResponseList(packageRepo.findAll());
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public PackageResponse getPackageById(int id) {
    PackageEntity entity = packageRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_EXIST));
    return packageMapper.toResponse(entity);
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public PackageResponse updatePackage(int id, PackageRequest request) {
    PackageEntity entity = packageRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_EXIST));
    entity.setName(request.getName());
    entity.setPrice(request.getPrice());
    entity.setDescription(request.getDescription());
    return packageMapper.toResponse(packageRepo.save(entity));
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void deletePackage(int id) {
    PackageEntity entity = packageRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_EXIST));
    packageRepo.delete(entity);
  }
}
