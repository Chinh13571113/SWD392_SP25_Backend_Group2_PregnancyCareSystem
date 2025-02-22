package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity,Integer> {

}
