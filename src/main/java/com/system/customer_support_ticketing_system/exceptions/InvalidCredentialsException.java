package com.system.customer_support_ticketing_system.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException{
    public InvalidCredentialsException() {
        super("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }
}
