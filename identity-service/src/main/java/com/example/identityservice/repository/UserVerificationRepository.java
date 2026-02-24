package com.example.identityservice.repository;

import com.example.identityservice.entity.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification,Long> {
}
