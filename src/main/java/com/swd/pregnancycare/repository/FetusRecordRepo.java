package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.FetusRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FetusRecordRepo extends JpaRepository<FetusRecordEntity,Integer> {
    Optional<List<FetusRecordEntity>> findByFetusId(int fetusId);
    Boolean existsByFetusIdAndDateRecordBetween(int fetusId, LocalDateTime startDate, LocalDateTime endDate);
}
