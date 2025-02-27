package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.BlogCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentRepo extends JpaRepository<BlogCommentEntity,Integer> {
}
