package com.system.customer_support_ticketing_system.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6,max = 20)
    private String password;

}
