package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepo extends JpaRepository<BlogEntity,Integer> {
}
