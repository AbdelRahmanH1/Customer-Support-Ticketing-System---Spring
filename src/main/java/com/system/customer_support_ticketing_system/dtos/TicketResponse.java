package com.system.customer_support_ticketing_system.dtos;

import com.system.customer_support_ticketing_system.enums.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private TicketStatus status;
    private LocalDateTime createdDate;

}
