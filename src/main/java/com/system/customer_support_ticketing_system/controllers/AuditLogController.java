package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.services.AuditLogService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit-logs")
@AllArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAuditLogs() {
       var audits =  auditLogService.getAll();
       return ResponseUtil.success("Get logged ", audits);
    }
}
