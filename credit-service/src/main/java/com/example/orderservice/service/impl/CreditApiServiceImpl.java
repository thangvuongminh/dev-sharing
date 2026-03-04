package com.example.orderservice.service.impl;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.example.orderservice.dto.AddCreditRequest;
import com.example.orderservice.dto.CreditBalanceDto;
import com.example.orderservice.dto.CreditTransactionDto;
import com.example.orderservice.dto.DeductCreditRequest;
import com.example.orderservice.entity.TransactionType;
import com.example.orderservice.entity.Wallet;
import com.example.orderservice.exception.ExceptionEnum;
import com.example.orderservice.repository.WalletRepository;
import com.example.orderservice.service.CreditApiService;
import com.example.orderservice.service.WalletService;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditApiServiceImpl implements CreditApiService {

  WalletRepository walletRepository;
  WalletService walletService;
  @Override
  @Transactional(readOnly = true)
  public CreditBalanceDto getBalance(Long userId) {
    Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new DevSharingException(
        ExceptionEnum.WALLET_NOT_FOUND));

    return CreditBalanceDto.builder()
        .userId(wallet.getUserId())
        .balance(wallet.getBalance())
        .build();
  }

  @Override
  @Transactional
  public CreditTransactionDto deductCredit(DeductCreditRequest request) {
    Wallet walletBefore = walletRepository.findByUserIdWithLock(request.getUserId())
        .orElseThrow(() -> new DevSharingException(ExceptionEnum.WALLET_NOT_FOUND));
    walletService.deductCredit(request.getUserId(),request.getAmount(), TransactionType.PURCHASE,request.getReason(),request.getReferenceId());
    Wallet walletAfter=walletRepository.findByUserIdWithLock(request.getUserId()).orElseThrow(() -> new DevSharingException(ExceptionEnum.WALLET_NOT_FOUND));;
    return CreditTransactionDto.builder()
        .userId(walletAfter.getUserId())
        .referenceId(request.getReferenceId())
        .amount(request.getAmount())
        .balanceBefore(walletBefore.getBalance())
        .balanceAfter(walletAfter.getBalance())
        .description(request.getReason())
        .createdAt(Instant.now())
        .type(TransactionType.PURCHASE.name())
        .build();
  }

  @Override
  public CreditTransactionDto addCredit(AddCreditRequest request) {
    Wallet walletBefore = walletRepository.findByUserIdWithLock(request.getUserId())
        .orElseThrow(() -> new DevSharingException(ExceptionEnum.WALLET_NOT_FOUND));
    walletService.addCredit(request.getUserId(),request.getAmount(), TransactionType.EARNING,request.getReason(),request.getReferenceId());
    Wallet walletAfter=walletRepository.findByUserIdWithLock(request.getUserId()).orElseThrow(() -> new DevSharingException(ExceptionEnum.WALLET_NOT_FOUND));;
    return CreditTransactionDto.builder()
        .userId(walletAfter.getUserId())
        .referenceId(request.getReferenceId())
        .amount(request.getAmount())
        .balanceBefore(walletBefore.getBalance())
        .balanceAfter(walletAfter.getBalance())
        .description(request.getReason())
        .createdAt(Instant.now())
        .type(TransactionType.EARNING.name())
        .build();
  }
}
