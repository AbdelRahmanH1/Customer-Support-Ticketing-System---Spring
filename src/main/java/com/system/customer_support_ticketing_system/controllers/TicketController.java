package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.dtos.CreateTicketRequest;
import com.system.customer_support_ticketing_system.dtos.UpdateTicketRequest;
import com.system.customer_support_ticketing_system.dtos.UpdateTicketStatusRequest;
import com.system.customer_support_ticketing_system.services.TicketService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
@Tag(name = "Ticket Controller",description = "Endpoints for managing Tickets")
@SecurityRequirement(name = "bearerAuth")
public class TicketController {

    private final TicketService ticketService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    @Operation(
            summary = "Create a new ticket",
            description = "Allows a user to create a new support ticket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — missing or invalid token"),
            @ApiResponse(responseCode = "403", description = "Forbidden — only users can create tickets")
    })
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        var userId = SecurityUtil.getUserId();

        var ticketResponse = ticketService.createTicket(userId,request);

        return ResponseUtil.success("Ticket created successfully", ticketResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get list of tickets",
            description = "Returns a paginated list of tickets. Admins can filter by user ID, users get their own tickets."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })

    public ResponseEntity<?> getTickets(
            @RequestParam(required = false, value = "user") Long userId,
            @RequestParam(defaultValue = "0") int page
    ) {
        var userID = SecurityUtil.getUserId();
        var userRole = SecurityUtil.getUserRole();
        var ticketDtoPage = ticketService.getTickets(userID, userId, page, userRole);
        return ResponseUtil.success("Fetched Tickets", ticketDtoPage);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a ticket by ID",
            description = "Returns details of a specific ticket. Admins can access any ticket, users can only access their own."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket fetched successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden — access denied"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<?> getTicketById(@PathVariable(name = "id") Long ticketId) {
        var userId = SecurityUtil.getUserId();
        var userRole = SecurityUtil.getUserRole();
        var ticketDto = ticketService.getTicketById(userId,userRole,ticketId);

        return ResponseUtil.success("Ticket fetched successfully", ticketDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a ticket",
            description = "Allows a user to update their own ticket before it's resolved"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden — only ticket owner can update"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<?> updateTicket(@PathVariable(name = "id") long ticketId,
                                          @Valid @RequestBody UpdateTicketRequest request)  {
        var userId = SecurityUtil.getUserId();
        var response = ticketService.updateTicket(userId, ticketId, request);
        return ResponseUtil.success("Ticket updated successfully", response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Update ticket status",
            description = "Allows an admin to update the status of any ticket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket status updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden — only admins can perform this"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<?> updateTicketStatus(@PathVariable(name = "id") Long ticketId,
                                                @Valid @RequestBody UpdateTicketStatusRequest request) {
        var adminId = SecurityUtil.getUserId();

        var response = ticketService.updateTicketStatus(adminId, ticketId, request);

        return ResponseUtil.success("Ticket updated successfully", response);
    }

}
