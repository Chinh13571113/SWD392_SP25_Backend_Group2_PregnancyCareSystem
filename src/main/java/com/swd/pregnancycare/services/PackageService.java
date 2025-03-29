package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.PackageDTO;
import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.PackageRepo;
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
        return packageRepo.findById(id);

    }

    @PreAuthorize("hasRole('ADMIN')")
    public void addNew(PackageEntity packageEntity){
        PackageEntity newPackage = new PackageEntity();
        newPackage.setName(packageEntity.getName());
        newPackage.setPrice(packageEntity.getPrice());
        newPackage.setDescription(packageEntity.getDescription());
        packageRepo.save(newPackage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void update (PackageEntity packageEntity){
        packageRepo.save(packageEntity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById (int id){
        packageRepo.deleteById(id);
    }

}
