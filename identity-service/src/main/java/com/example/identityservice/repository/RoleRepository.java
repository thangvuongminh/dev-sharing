package com.example.identityservice.repository;

import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleEnum name);
}
