package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.PackageRepo;
import com.swd.pregnancycare.request.PackageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {
    @Autowired
    private PackageRepo packageRepo ;

    public PackageService(PackageRepo packageRepo){
        this.packageRepo= packageRepo ;
    }



    @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
    public List<PackageEntity> getAll(){
        return packageRepo.findAll();
    }



    @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
    public Optional<PackageEntity> getbyId(int id){
        packageRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.PACKAGE_NOT_EXIST));
        return packageRepo.findById(id);
    }



    @PreAuthorize("hasRole('ADMIN')")
    public PackageEntity addNew(PackageRequest request){
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setName(request.getName());
        packageEntity.setPrice(request.getPrice());
        packageEntity.setDescription(request.getDescription());
        PackageEntity newPackage = packageRepo.save(packageEntity);
        return newPackage;
    }



    @PreAuthorize("hasRole('ADMIN')")
    public void update (int id, PackageRequest request){
        PackageEntity packageEntity = packageRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.PACKAGE_NOT_EXIST));
        packageEntity.setName(request.getName());
        packageEntity.setPrice(request.getPrice());
        packageEntity.setDescription(request.getDescription());
        packageRepo.save(packageEntity);
    }



    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById (int id){
        packageRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.PACKAGE_NOT_EXIST));
        packageRepo.deleteById(id);
    }

}
