package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.services.AuditLogService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit-logs")
@AllArgsConstructor
@Tag(name = "Audit Logs", description = "Endpoints for admin-only audit log access")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all audit logs (ADMIN only)")
    public ResponseEntity<?> getAuditLogs() {
       var audits =  auditLogService.getAll();
       return ResponseUtil.success("Get logged ", audits);
    }
}
