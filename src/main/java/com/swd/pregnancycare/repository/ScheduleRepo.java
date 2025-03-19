package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<ScheduleEntity,Integer> {
    List<ScheduleEntity> findByAppointmentId(int appointmentId);
//    List<ScheduleEntity> findByIsNoticeFalseAndDateRemindBefore(LocalDateTime now);
//    List<ScheduleEntity> findByIsNoticeFalseAndDateBeforeOrEqual(LocalDateTime date);
    Boolean existsByAppointmentId(int appointmentId);
    @Query("SELECT s FROM schedule s " +
            "WHERE s.isNotice = false AND DATE(s.dateRemind) = CURRENT_DATE")
    List<ScheduleEntity> findSchedulesToNotify();
    void deleteByAppointmentId(int appointmentId);
}
