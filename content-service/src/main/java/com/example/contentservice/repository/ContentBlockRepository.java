package com.example.contentservice.repository;

import com.example.contentservice.entity.ContentBlock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentBlockRepository extends JpaRepository<ContentBlock,Long> {
  List<ContentBlock> findByContentIdAndPositionIsNotNullOrderByPositionAsc(Long contentId);
  List<ContentBlock> findByParentBlockIdAndPositionIsNotNullOrderByPosition(Long contentId);

}
