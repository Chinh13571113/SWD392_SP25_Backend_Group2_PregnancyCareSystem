package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.PackageEntity;
import com.swd.pregnancycare.entity.UserPackageEntity;
import com.swd.pregnancycare.repository.UserPackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPackageService {
    @Autowired
    private UserPackageRepo userpackageRepo ;

    public UserPackageService(UserPackageRepo userpackageRepo){
        this.userpackageRepo= userpackageRepo ;
    }
    public void update (UserPackageEntity userpackageEntity){
        userpackageRepo.save(userpackageEntity);
    }
    public List<UserPackageEntity> getAll(){
        return userpackageRepo.findAll();
    }
    public Optional<UserPackageEntity> getbyId(int id){
        return userpackageRepo.findById(id);

    }
    public void addNew(UserPackageEntity userpackageEntity){
        userpackageRepo.save(userpackageEntity);
    }
    public void delete (int id){
        userpackageRepo.deleteById(id);
    }
}
