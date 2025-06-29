package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.dtos.ReplyRequest;
import com.system.customer_support_ticketing_system.services.TicketReplyService;
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
@RequestMapping("/tickets/{ticketId}/replies")
@AllArgsConstructor
@Tag(name = "Ticket Replies", description = "Endpoints for managing replies on support tickets")
@SecurityRequirement(name = "bearerAuth")
public class TicketReplyController {

    private final TicketReplyService ticketReplyService;


    @PostMapping()
    @Operation(
            summary = "Add a reply to a ticket",
            description = "Allows a user or admin to add a reply to a specific support ticket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reply added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> addReply(
            @PathVariable(name = "ticketId") Long ticketId,
            @Valid @RequestBody ReplyRequest request
    ) {
        Long userId =  SecurityUtil.getUserId();
        boolean isAdmin = SecurityUtil.isCurrentUserAdmin();
        var response  = ticketReplyService.addReply(userId, isAdmin, ticketId, request);
        return ResponseUtil.success("Message sent successfully",response, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(
            summary = "Get replies of a ticket",
            description = "Retrieve all replies for a specific ticket. Users see only their tickets; admins can access all."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Replies fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<?> getReplies(@PathVariable(name = "ticketId") Long ticketId) {
        Long userId = SecurityUtil.getUserId();
        boolean isAdmin = SecurityUtil.isCurrentUserAdmin();

        var response = ticketReplyService.getReply(userId, isAdmin, ticketId);

        return ResponseUtil.success("Replies fetched successfully", response);
    }

    @DeleteMapping()
    @Operation(
            summary = "Delete all replies of a ticket",
            description = "Allows an admin to delete all replies related to a specific ticket"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Replies deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden — admin only")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteReply(@PathVariable(name = "ticketId") Long ticketId) {
        ticketReplyService.deleteReply(ticketId);
        return ResponseUtil.success("Message sent successfully",null);
    }
}
