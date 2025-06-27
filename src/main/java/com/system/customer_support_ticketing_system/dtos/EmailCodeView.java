package com.system.customer_support_ticketing_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailCodeView {
    private String email;
    private String passwordResetCode;
}
