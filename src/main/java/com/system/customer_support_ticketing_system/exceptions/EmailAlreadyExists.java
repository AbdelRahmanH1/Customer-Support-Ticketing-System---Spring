package com.system.customer_support_ticketing_system.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExists extends ApiException {

    public EmailAlreadyExists() {
        super("Email already exist",HttpStatus.CONFLICT);
    }
}
