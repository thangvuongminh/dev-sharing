package com.example.contentservice.repository;

import com.example.contentservice.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long>, JpaSpecificationExecutor<Content> {
}
