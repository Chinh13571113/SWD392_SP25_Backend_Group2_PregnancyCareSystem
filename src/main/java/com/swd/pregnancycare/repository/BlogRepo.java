package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepo extends JpaRepository<BlogEntity,Integer> {
  Optional<BlogEntity> findByIdAndDeletedFalse(int id);
  Optional<BlogEntity> findByIdAndDeletedTrue(int id);
  void deleteByIdAndDeletedTrue(int id);
}
