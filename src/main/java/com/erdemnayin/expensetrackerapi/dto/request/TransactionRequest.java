package com.erdemnayin.expensetrackerapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    // Transaction -> Create - Update Requests
    private String detail;

    private Double purchaseAmount;

    private Long userId;
}
