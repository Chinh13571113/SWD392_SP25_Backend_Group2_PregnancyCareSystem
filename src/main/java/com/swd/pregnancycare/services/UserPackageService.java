package com.swd.pregnancycare.services;

import ch.qos.logback.core.spi.ErrorCodes;
import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.entity.UserPackageEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.PackageRepo;
import com.swd.pregnancycare.repository.UserPackageRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserPackageService {
  @Autowired
  private final UserPackageRepo userpackageRepo;
  @Autowired
  private UserServicesImp userServicesImp;
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private PackageRepo packageRepo;

  public UserPackageService(UserPackageRepo userpackageRepo) {
    this.userpackageRepo = userpackageRepo;
  }


  public List<UserPackageEntity> getAllMyPackages() {
    return userpackageRepo.findAll();
  }

  @PreAuthorize("hasRole('MEMBER')")
  public void addNew(int packageId) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> user = userRepo.findByIdAndStatusTrue(userResponse.getId());
    UserEntity userEntity = user.get();

    UserPackageEntity userPackageEntity = new UserPackageEntity();
    userPackageEntity.setDateBegin(LocalDateTime.now());
    userPackageEntity.setDateEnd(LocalDateTime.now().plusDays(30));
    userPackageEntity.setUser(userEntity);
    // Package
    PackageEntity packageEntity = packageRepo.findById(packageId).orElseThrow(()-> new AppException(ErrorCode.PACKAGE_NOT_EXIST));
    userPackageEntity.setPackageEntity(packageEntity);


    userpackageRepo.save(userPackageEntity);
  }

}
