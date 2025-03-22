package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.UserPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPackageRepo extends JpaRepository<UserPackageEntity,Integer> {
}
