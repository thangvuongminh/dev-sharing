package com.example.orderservice.mapper;

import com.example.orderservice.dto.response.WalletDto;
import com.example.orderservice.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,
unmappedSourcePolicy = ReportingPolicy.IGNORE,imports = {java.math.BigDecimal.class})
public interface WalletMapper {
  @Mapping(target = "balanceInVnd",expression = "java(wallet.getBalance().multiply(new BigDecimal(\"1000\")))")
  WalletDto toWalletDto(Wallet wallet);
}
