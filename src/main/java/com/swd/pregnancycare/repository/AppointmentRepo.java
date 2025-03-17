package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.AppointmentEntity;
import com.swd.pregnancycare.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Integer> {
    List<AppointmentEntity> findByFetusId(int fetusId);
    List<AppointmentEntity> findByUsersId(int usersId);
    boolean existsByDateIssueAndUsers(LocalDateTime dateIssue, UserEntity users);

}
