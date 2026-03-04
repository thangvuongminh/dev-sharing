package com.example.contentservice.client;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.contentservice.client.request.CreditTransactionRequest;
import com.example.contentservice.client.response.CreditTransactionDto;
import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "credit-service")
public interface CreditServiceClient {
  @PostMapping("/api/v1/transactions/deduct")
  ApiResponse<Void> deduct(@RequestBody CreditTransactionRequest request);
  @PostMapping("/api/v1/transactions/add")
  ApiResponse<Void> addCredit(@RequestBody CreditTransactionRequest request);

}
