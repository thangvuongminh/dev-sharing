package com.example.identityservice.repository;

import com.example.identityservice.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    @EntityGraph(attributePaths = {"userRole","userRole.role"})
    User findAllRoleByUserName(String userName);
}
