package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.dtos.CreateTicketRequest;
import com.system.customer_support_ticketing_system.services.TicketService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping
    public ResponseEntity<?> getTickets(
            @RequestParam(required = false, value = "user") Long userId,
            @RequestParam(defaultValue = "0") int page
    ) {
        var userID = SecurityUtil.getUserId();
        var userRole = SecurityUtil.getUserRole();
        var ticketDtoPage = ticketService.updateTicket(userID, userId, page, userRole);
        return ResponseUtil.success("Fetched Tickets", ticketDtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable(name = "id") Long ticketId) {
        var userId = SecurityUtil.getUserId();
        var userRole = SecurityUtil.getUserRole();
        var ticketDto = ticketService.getTicketById(userId,userRole,ticketId);

        return ResponseUtil.success("Ticket fetched successfully", ticketDto);
    }
}
