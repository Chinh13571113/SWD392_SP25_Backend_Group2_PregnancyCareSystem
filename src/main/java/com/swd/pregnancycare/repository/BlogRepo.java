package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<BlogEntity,Integer> {

}
