package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.ArticleSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSectionRepo extends JpaRepository<ArticleSectionEntity, Integer> {
}
