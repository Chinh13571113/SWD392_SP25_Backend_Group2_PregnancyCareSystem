package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.WhoStandardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhoStandardRepo extends JpaRepository<WhoStandardEntity,Integer> {
    WhoStandardEntity findTopByFetusWeekLessThanEqualOrderByFetusWeekDesc(int week);
    WhoStandardEntity findByFetusWeek(int week);
}
