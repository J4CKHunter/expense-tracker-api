package com.erdemnayin.expensetrackerapi.dto.response;

import com.erdemnayin.expensetrackerapi.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;

    private String detail;

    private LocalDateTime dateTime;

    private Double purchaseAmount;

    private Long userId;

}
