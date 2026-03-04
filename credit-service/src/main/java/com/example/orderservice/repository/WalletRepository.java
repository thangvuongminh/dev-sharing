package com.example.orderservice.repository;

import com.example.orderservice.entity.Wallet;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select w from Wallet w where  w.userId= :userId")
  Optional<Wallet> findByUserIdWithLock(@Param("userId") Long userId);
  Optional<Wallet> findByUserId(Long userId);

}
