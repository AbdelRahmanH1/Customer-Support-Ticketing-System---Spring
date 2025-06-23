package com.system.customer_support_ticketing_system.exceptions;

import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends ApiException{
    public TicketNotFoundException() {
        super("Ticket not found", HttpStatus.NOT_FOUND);
    }
}
