package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<GroupEntity, Integer> {
}
