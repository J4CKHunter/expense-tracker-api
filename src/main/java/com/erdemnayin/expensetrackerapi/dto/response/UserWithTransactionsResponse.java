package com.erdemnayin.expensetrackerapi.dto.response;

import com.erdemnayin.expensetrackerapi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithTransactionsResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private List<TransactionResponse> transactionResponses;
}
