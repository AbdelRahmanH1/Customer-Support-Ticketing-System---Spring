package com.system.customer_support_ticketing_system.exceptions;


import org.springframework.http.HttpStatus;

public class EmailSendingException extends ApiException {
    public EmailSendingException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
