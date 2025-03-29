package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PackageEntity> getAll(){
        return packageRepo.findAll();
    }
    public Optional<PackageEntity> getbyId(int id){
        return packageRepo.findById(id);

    }
    public void addNew(PackageEntity packageEntity){

        packageRepo.save(packageEntity);
    }
    public void update (PackageEntity packageEntity){
        packageRepo.save(packageEntity);
    }
    public void deleteById (int id){
        packageRepo.deleteById(id);
    }

}
