package com.system.customer_support_ticketing_system.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiException{
    public InvalidTokenException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
