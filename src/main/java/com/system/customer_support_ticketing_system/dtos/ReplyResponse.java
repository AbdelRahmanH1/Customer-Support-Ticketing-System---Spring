package com.system.customer_support_ticketing_system.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyResponse {
    private Long id;
    private Long ticketId;
    private Long userId;
    private String message;
    private LocalDateTime createdAt;
}
