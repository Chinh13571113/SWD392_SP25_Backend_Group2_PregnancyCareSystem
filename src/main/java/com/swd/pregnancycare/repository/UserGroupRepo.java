package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepo extends JpaRepository<UserGroupEntity, Integer> {
  @Query("SELECT u FROM user_group u WHERE u.user.id = :userId AND u.group.id = :groupId AND u.deleted = false")
  Optional<UserGroupEntity> findByUserIdAndGroupId(int userId, int groupId);
}
