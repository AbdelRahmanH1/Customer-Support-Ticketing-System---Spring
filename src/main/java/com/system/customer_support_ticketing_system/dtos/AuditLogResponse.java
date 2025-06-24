package com.system.customer_support_ticketing_system.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLogResponse {
    private Long id;
    private String action;
    private Long userId;
    private String metadata;
    private LocalDateTime createdAt;


}
