package com.example.orderservice.service;

import com.example.orderservice.dto.request.DepositRequest;
import com.example.orderservice.entity.Transaction;
import com.example.orderservice.entity.TransactionType;
import com.example.orderservice.entity.Wallet;
import java.math.BigDecimal;

public interface WalletService {
  public Wallet getOrCreateWallet(Long userId);
  public Wallet createWallet(Long userId);
  public Wallet getWallet(Long userId);
  public Wallet deposit(DepositRequest depositRequest);
  public void recordTransaction(Long userId, TransactionType type, BigDecimal amount,BigDecimal balanceBefore,BigDecimal balanceAfter,String description,String referenceId);
  public void deductCredit(Long userId,BigDecimal amount,TransactionType type,String description,String referenceId);
  public void addCredit(Long userId,BigDecimal amount,TransactionType type,String description,String referenceId);
}
