package com.example.orderservice.mapper;

import com.example.orderservice.dto.WithdrawalRequestDto;
import com.example.orderservice.entity.WithdrawalRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WithdrawalMapper {

  WithdrawalRequestDto toWithdrawalRequestDto(WithdrawalRequest withdrawalRequest);
}
