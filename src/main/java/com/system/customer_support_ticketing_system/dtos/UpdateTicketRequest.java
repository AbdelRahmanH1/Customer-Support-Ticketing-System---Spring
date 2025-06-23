package com.system.customer_support_ticketing_system.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTicketRequest {


    @Size(min = 5, max = 50)
    private String title;


    @Size(min = 10, max = 100)
    private String description;
}
