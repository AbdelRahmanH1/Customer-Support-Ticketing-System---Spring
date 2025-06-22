package com.system.customer_support_ticketing_system.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiException{
    public InvalidTokenException() {
        super("Invalid Token", HttpStatus.BAD_REQUEST);
    }
}
