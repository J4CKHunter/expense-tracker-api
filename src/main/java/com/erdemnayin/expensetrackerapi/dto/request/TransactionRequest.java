package com.erdemnayin.expensetrackerapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    // Transaction -> Create - Update Requests

    @NotBlank(message = "detail couldn't be blank.")
    @Size(min=3, message="detail must be at least 3 characters long")
    private String detail;

    private Double purchaseAmount;

    private Long userId;
}
