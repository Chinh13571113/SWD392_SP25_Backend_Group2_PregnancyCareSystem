package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.FetusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetusRepo extends JpaRepository<FetusEntity,Integer> {
}
