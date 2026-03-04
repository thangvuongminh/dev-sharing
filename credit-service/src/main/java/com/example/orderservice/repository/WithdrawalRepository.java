package com.example.orderservice.repository;

import com.example.orderservice.entity.Wallet;
import com.example.orderservice.entity.WithDrawStatus;
import com.example.orderservice.entity.WithdrawalRequest;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepository extends CrudRepository<WithdrawalRequest,Long>,
    JpaSpecificationExecutor<WithdrawalRequest> {
  Optional<WithdrawalRequest> findByUserId(Long userId);
  Optional<WithdrawalRequest> findByUserIdAndStatus(Long userId, WithDrawStatus status);
  Page<WithdrawalRequest> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
  Page<WithdrawalRequest> findByStatusOrderByCreatedAtAsc(WithDrawStatus status, Pageable pageable);

}
