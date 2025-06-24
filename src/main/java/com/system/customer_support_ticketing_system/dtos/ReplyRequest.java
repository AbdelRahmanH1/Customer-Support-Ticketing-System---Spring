package com.system.customer_support_ticketing_system.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReplyRequest {
    @NotBlank
    @Size(min = 1, max = 100 ,message = "Message length must be between 1 to 100 characters")
    private String message;
}
