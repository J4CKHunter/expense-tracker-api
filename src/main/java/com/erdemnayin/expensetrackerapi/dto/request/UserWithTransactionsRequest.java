package com.erdemnayin.expensetrackerapi.dto.request;

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
public class UserWithTransactionsRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private List<TransactionRequest> transactionRequests;

}
