package com.example.orderservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.orderservice.dto.response.WalletDto;
import com.example.orderservice.entity.Wallet;
import com.example.orderservice.mapper.WalletMapper;
import com.example.orderservice.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wallets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletInternalController {
  WalletService walletService;
  WalletMapper walletMapper;
  @GetMapping("internal/{userId}")
  @Operation(summary = "Get or create wallet(Internal Api)",description = "Internal APIs for wallet management (inter-service calls)" )
  public ResponseEntity<ApiResponse<WalletDto>> getOrCreateWallet(@PathVariable String userId) {
    Long userIdLong = Long.parseLong(userId);
    Wallet wallet= walletService.getOrCreateWallet(userIdLong);
    WalletDto walletDto = walletMapper.toWalletDto(wallet);
    return  ResponseEntity.ok().body(ApiResponse.success(walletDto));
  }
}
