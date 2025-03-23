package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.PossessDegreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PossessDegreeRepo extends JpaRepository<PossessDegreeEntity, Integer> {
  @Query("SELECT p FROM possess_degree p WHERE p.user.id = :userId")
  List<PossessDegreeEntity> findByUserId(@Param("userId") int userId);
}
