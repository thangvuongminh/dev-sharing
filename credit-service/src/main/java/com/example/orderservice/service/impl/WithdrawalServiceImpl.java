package com.example.orderservice.service.impl;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.dev.sharing.api.commom.exception.DevSharingExceptionInfo;
import com.example.orderservice.dto.WithdrawalRequestDto;
import com.example.orderservice.dto.request.ReviewWithdrawalRequest;
import com.example.orderservice.entity.TransactionType;
import com.example.orderservice.entity.Wallet;
import com.example.orderservice.entity.WithDrawStatus;
import com.example.orderservice.entity.WithdrawalRequest;
import com.example.orderservice.exception.ExceptionEnum;
import com.example.orderservice.repository.WalletRepository;
import com.example.orderservice.repository.WithdrawalRepository;
import com.example.orderservice.service.WalletService;
import com.example.orderservice.service.WithdrawalService;
import com.example.orderservice.util.UserContextExtractor;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WithdrawalServiceImpl implements WithdrawalService {
  BigDecimal MIN_WITHDRAWAL = new BigDecimal("10");
  WithdrawalRepository withdrawalRepository;
  WalletService walletService;
  WalletRepository walletRepository;
  @Override
  @Transactional
  public WithdrawalRequest createWithdrawalRequest(WithdrawalRequestDto withdrawalRequestDto) {
    Long userId = UserContextExtractor.getUserId();
    if (withdrawalRequestDto.getAmount().compareTo(MIN_WITHDRAWAL) <= 0) {
      throw new DevSharingException(ExceptionEnum.MINIMUM_WITHDRAWAL_NOT_FULFILL, new Object[]{withdrawalRequestDto.getAmount(), MIN_WITHDRAWAL});
    }
    Wallet wallet = walletRepository.findByUserIdWithLock(userId).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.WALLET_NOT_FOUND)
    );
    if (wallet.getBalance().compareTo(withdrawalRequestDto.getAmount()) <= 0) {
      throw new DevSharingException(ExceptionEnum.INSUFFICIENT_BALANCE);
    }
    var withdrawalRequest = withdrawalRepository.findByUserIdAndStatus(userId,
        WithDrawStatus.PENDING);
    if (withdrawalRequest.isPresent()) {
      throw new DevSharingException(ExceptionEnum.PENDING_WITHDRAWAL_EXISTS);
    }
    WithdrawalRequest withdrawalRequest1=WithdrawalRequest.builder()
        .createdAt(Instant.now())
        .userId(userId)
        .accountHolderName(withdrawalRequestDto.getAccountHolderName())
        .bankName(withdrawalRequestDto.getBankName())
        .amount(withdrawalRequestDto.getAmount())
        .bankAccountNumber(withdrawalRequestDto.getBankAccountNumber())
        .note(withdrawalRequestDto.getNote())
        .status(WithDrawStatus.PENDING)
        .updatedAt(Instant.now())
        .build();
    withdrawalRepository.save(withdrawalRequest1);
    return withdrawalRequest1;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<WithdrawalRequest> getUserWithdrawalRequests(Pageable pageable) {
    return withdrawalRepository.findByUserIdOrderByCreatedAtDesc(UserContextExtractor.getUserId(), pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public WithdrawalRequest getWithdrawalRequest(Long requestId) {
    WithdrawalRequest withdrawalRequest = withdrawalRepository.findById(requestId).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.WITHDRAWAL_NOT_EXIST, new Object[]{requestId})
    );
    if (!withdrawalRequest.getUserId().equals(UserContextExtractor.getUserId())) {
      throw new DevSharingException(ExceptionEnum.WITHDRAWAL_NOT_AUTHORIZE);
    }
    return withdrawalRequest;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<WithdrawalRequest> getPendingWithdrawalRequests(Pageable pageable) {
    return withdrawalRepository.findByStatusOrderByCreatedAtAsc(WithDrawStatus.PENDING, pageable);
  }

  @Override
  public WithdrawalRequest reviewWithdrawalRequest( Long withdrawalId,
      ReviewWithdrawalRequest reviewWithdrawalRequest) {
    WithdrawalRequest withdrawalRequest = withdrawalRepository.findById(withdrawalId).orElseThrow(
        () -> new DevSharingException(ExceptionEnum.WITHDRAWAL_NOT_EXIST, new Object[]{withdrawalId})
    );
    if(!withdrawalRequest.getStatus().equals(WithDrawStatus.PENDING)) {
      throw new DevSharingException(ExceptionEnum.WITHDRAWAL_ALREADY_PROCESSED);
    }
    if (reviewWithdrawalRequest.getApproved()){
      walletService.deductCredit(withdrawalRequest.getUserId(),withdrawalRequest.getAmount(),
          TransactionType.WITHDRAW,"Withdrawal approve " + reviewWithdrawalRequest.getAdminNote(), String.valueOf(withdrawalId));
      withdrawalRequest.setStatus(WithDrawStatus.APPROVE);
    }else {
      withdrawalRequest.setStatus(WithDrawStatus.REJECT);
    }
    withdrawalRequest.setReviewedAt(Instant.now());
    withdrawalRequest.setAdminNote(reviewWithdrawalRequest.getAdminNote());
    withdrawalRequest.setReviewedBy(UserContextExtractor.getUserId());
    withdrawalRequest.setUpdatedAt(Instant.now());
    withdrawalRepository.save(withdrawalRequest);
    return withdrawalRequest;
  }


}
