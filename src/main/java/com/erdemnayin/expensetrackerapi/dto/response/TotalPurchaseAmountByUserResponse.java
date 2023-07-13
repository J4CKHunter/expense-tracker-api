package com.erdemnayin.expensetrackerapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalPurchaseAmountByUserResponse {

    private Long userId;

    private Double totalPurchaseAmount;

    private List<TransactionResponse> transactions;
}
