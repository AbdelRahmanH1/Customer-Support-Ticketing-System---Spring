package com.system.customer_support_ticketing_system.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6,max = 20)
    private String newPassword;

    @NotBlank
    @Size(min = 6,max = 6)
    private String code;
}
