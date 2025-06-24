package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.dtos.ReplyRequest;
import com.system.customer_support_ticketing_system.exceptions.ApiException;
import com.system.customer_support_ticketing_system.exceptions.TicketNotFoundException;
import com.system.customer_support_ticketing_system.mappers.TicketReplyMapper;
import com.system.customer_support_ticketing_system.repositories.TicketReplyRepository;
import com.system.customer_support_ticketing_system.repositories.TicketRepository;
import com.system.customer_support_ticketing_system.services.TicketReplyService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/tickets/{ticketId}")
@AllArgsConstructor
public class TicketReplyController {

    private final TicketReplyService ticketReplyService;

    @PostMapping("/replies")
    public ResponseEntity<?> addReply(
            @PathVariable(name = "ticketId") Long ticketId,
            @Valid @RequestBody ReplyRequest request
    ) {
        Long userId =  SecurityUtil.getUserId();
        boolean isAdmin = SecurityUtil.isCurrentUserAdmin();
        var response  = ticketReplyService.addReply(userId, isAdmin, ticketId, request);
        return ResponseUtil.success("Message sent successfully",response, HttpStatus.CREATED);
    }

    @GetMapping("/replies")
    public ResponseEntity<?> getReplies(@PathVariable(name = "ticketId") Long ticketId) {
        Long userId = SecurityUtil.getUserId();
        boolean isAdmin = SecurityUtil.isCurrentUserAdmin();

        var response = ticketReplyService.getReply(userId, isAdmin, ticketId);

        return ResponseUtil.success("Replies fetched successfully", response);
    }

}
