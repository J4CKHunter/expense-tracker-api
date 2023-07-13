package com.erdemnayin.expensetrackerapi.dto.request;

import com.erdemnayin.expensetrackerapi.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "firstName couldn't be blank.")
    @Size(min=3, message="firstName must be at least 3 characters long")
    private String firstName;

    @NotBlank(message = "lastName couldn't be blank.")
    @Size(min=3, message="lastName must be at least 3 characters long")
    private String lastName;

    @NotBlank(message = "email couldn't be blank.")
    @Email(message = "Provide a valid email adress.")
    private String email;

    @NotBlank(message = "password couldn't be blank.")
    @Size(min=3, message="password must be at least 3 characters long")
    private String password;
    private Role role;
    private List<TransactionRequest> transactionRequests;

}
