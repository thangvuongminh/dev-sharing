package com.example.orderservice.service;

import com.example.orderservice.dto.AddCreditRequest;
import com.example.orderservice.dto.CreditBalanceDto;
import com.example.orderservice.dto.CreditTransactionDto;
import com.example.orderservice.dto.DeductCreditRequest;

public interface CreditApiService {
  CreditBalanceDto getBalance(Long userId);
  CreditTransactionDto deductCredit(DeductCreditRequest request);
  CreditTransactionDto addCredit(AddCreditRequest request);

}
