package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepo extends JpaRepository<ScheduleEntity,Integer> {

}
