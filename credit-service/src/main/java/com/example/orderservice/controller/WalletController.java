package com.example.orderservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.orderservice.dto.request.DepositRequest;
import com.example.orderservice.dto.response.WalletDto;
import com.example.orderservice.entity.Wallet;
import com.example.orderservice.mapper.WalletMapper;
import com.example.orderservice.service.WalletService;
import com.example.orderservice.util.UserContextExtractor;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WalletController {
  WalletService walletService;
  WalletMapper  walletMapper;
  @GetMapping
  @PreAuthorize("hasAnyRole('CONSUMER','CREATOR','ADMIN')")
  public ResponseEntity<ApiResponse<WalletDto>> getWallets(){
    Wallet wallet =  walletService.getWallet(UserContextExtractor.getUserId());
    WalletDto walletDto = walletMapper.toWalletDto(wallet);
    int i=0;
    return  ResponseEntity.ok().body(ApiResponse.success(walletDto));
  }
  @PostMapping("/deposit")
  @PreAuthorize("hasAnyRole('CONSUMER','CREATOR')")
  public ResponseEntity<ApiResponse<WalletDto>> deposit(@Valid @RequestBody DepositRequest depositRequest){
    Wallet wallet = walletService.deposit(depositRequest);
    WalletDto walletDto = walletMapper.toWalletDto(wallet);
    return  ResponseEntity.ok().body(ApiResponse.success(walletDto));
  }

}
