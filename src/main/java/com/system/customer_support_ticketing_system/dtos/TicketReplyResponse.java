package com.system.customer_support_ticketing_system.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketReplyResponse {
    private Long id;
    private String senderRole;
    private String message;
    private LocalDateTime createdAt;
}
