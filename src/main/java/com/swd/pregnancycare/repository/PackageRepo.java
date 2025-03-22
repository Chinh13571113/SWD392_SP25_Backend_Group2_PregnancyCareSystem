package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PackageRepo extends JpaRepository<PackageEntity,Integer> {
}
