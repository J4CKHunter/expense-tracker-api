package com.erdemnayin.expensetrackerapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalPurchaseAmountResponse {
    private Long userId;

    private Double totalPurchaseAmount;
}
