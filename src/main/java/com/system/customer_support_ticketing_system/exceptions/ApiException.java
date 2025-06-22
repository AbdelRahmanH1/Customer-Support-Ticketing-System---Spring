package com.system.customer_support_ticketing_system.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException{
    private final HttpStatus status;
    public ApiException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
