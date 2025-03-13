package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<GroupEntity, Integer> {
  Optional<GroupEntity> findByName(String name);
}
