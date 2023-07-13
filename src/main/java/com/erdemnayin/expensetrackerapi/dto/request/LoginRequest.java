package com.erdemnayin.expensetrackerapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "email couldn't be blank.")
    @Email(message = "Provide a valid email adress.")
//    @Size(min=3, message="Name must be at least 3 characters long")
//    @Min(value = 0, message = "Fees value must be greater than zero")
//    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String email;
    private String password;
}
