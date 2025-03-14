package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Integer> {
    List<AppointmentEntity> findByFetusId(int fetusId);
    List<AppointmentEntity> findByUsersId(int usersId);
}
