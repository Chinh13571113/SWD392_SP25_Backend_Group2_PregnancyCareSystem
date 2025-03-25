package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmailAndStatusTrue(String email);
    Boolean existsByEmailAndStatusTrue(String email);
    Optional<UserEntity> findByIdAndStatusTrue(int id);
    @Query("SELECT COUNT(u) FROM users u WHERE u.status = true")
    long countActiveUsers();
}
