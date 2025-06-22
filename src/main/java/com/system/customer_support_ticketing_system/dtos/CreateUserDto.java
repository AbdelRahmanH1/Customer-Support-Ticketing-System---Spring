package com.system.customer_support_ticketing_system.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotBlank(message = "name is required")
    @Size(min = 4, max = 25,message = "Name length must be between 5 and 25 characters")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "enter valid email")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6,max = 20)
    private String password;
}
