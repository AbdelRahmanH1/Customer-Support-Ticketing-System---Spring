package com.system.customer_support_ticketing_system.services;

import com.system.customer_support_ticketing_system.dtos.AuditLogResponse;
import com.system.customer_support_ticketing_system.entities.AuditLog;
import com.system.customer_support_ticketing_system.mappers.AuditLogMapper;
import com.system.customer_support_ticketing_system.repositories.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;
    public void log(String action,Long userId,String metadata){
        var log = new AuditLog()
                .withAction(action)
                .withUserId(userId)
                .withMetadata(metadata);
        auditLogRepository.save(log);

    }
    public List<AuditLogResponse> getAll(){
        var audits = auditLogRepository.findAll();
        return audits.stream()
                .map(auditLogMapper::toAuditLogResponse)
                .toList();
    }
}
