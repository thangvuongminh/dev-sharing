package com.example.orderservice.service;

import com.example.orderservice.dto.WithdrawalRequestDto;
import com.example.orderservice.dto.request.ReviewWithdrawalRequest;
import com.example.orderservice.entity.WithdrawalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WithdrawalService {
  public WithdrawalRequest createWithdrawalRequest(WithdrawalRequestDto withdrawalRequestDto);
  public Page<WithdrawalRequest> getUserWithdrawalRequests(Pageable pageable);
  public WithdrawalRequest getWithdrawalRequest(Long requestId);
  public Page<WithdrawalRequest> getPendingWithdrawalRequests(Pageable pageable);
  public WithdrawalRequest reviewWithdrawalRequest(Long withdrawalId,
      ReviewWithdrawalRequest reviewWithdrawalRequest);
}
