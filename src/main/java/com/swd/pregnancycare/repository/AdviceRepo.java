package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.AdviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviceRepo extends JpaRepository<AdviceEntity, Integer> {
}
