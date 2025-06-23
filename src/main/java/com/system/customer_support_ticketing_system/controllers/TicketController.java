package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.dtos.CreateTicketRequest;
import com.system.customer_support_ticketing_system.services.TicketService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        var userId = SecurityUtil.getUserId();

        var ticketResponse = ticketService.createTicket(userId,request);

        return ResponseUtil.success("Ticket created successfully", ticketResponse, HttpStatus.CREATED);
    }
}
