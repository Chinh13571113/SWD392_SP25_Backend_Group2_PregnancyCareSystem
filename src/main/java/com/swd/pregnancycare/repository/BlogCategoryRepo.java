package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.BlogCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogCategoryRepo extends JpaRepository<BlogCategoryEntity,Integer> {
  Optional<BlogCategoryEntity> findByName(String name);
}
