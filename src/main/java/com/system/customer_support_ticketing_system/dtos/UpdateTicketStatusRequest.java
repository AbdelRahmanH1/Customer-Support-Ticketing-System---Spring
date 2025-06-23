package com.system.customer_support_ticketing_system.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTicketStatusRequest {
    @NotNull(message = "Status is required")
    private String status;
}
