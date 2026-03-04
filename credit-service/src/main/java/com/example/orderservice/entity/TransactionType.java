package com.example.orderservice.entity;

public enum TransactionType {
  DEPOSIT, // consumer  deposit money
  PURCHASE, // consumer purchase content
  EARNING, // creator  receive payment from  the buyer
  WITHDRAW, // creator  withdraw money
  REFUND,
}
