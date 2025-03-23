package com.swd.pregnancycare.repository;

import com.swd.pregnancycare.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepo extends JpaRepository<CertificateEntity, Integer> {
}
