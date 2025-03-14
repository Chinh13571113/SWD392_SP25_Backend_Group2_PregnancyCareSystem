package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.FetusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FetusRepo extends JpaRepository<FetusEntity,Integer> {
    List<FetusEntity> findByUserId(int userId);
    Boolean existsByIdAndUserId(int fetusId, int userId);
}
