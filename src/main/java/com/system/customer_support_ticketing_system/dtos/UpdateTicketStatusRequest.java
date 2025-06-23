package com.system.customer_support_ticketing_system.dtos;

import com.system.customer_support_ticketing_system.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTicketStatus {
    @NotNull(message = "Status is required")
    private TicketStatus status;
}
