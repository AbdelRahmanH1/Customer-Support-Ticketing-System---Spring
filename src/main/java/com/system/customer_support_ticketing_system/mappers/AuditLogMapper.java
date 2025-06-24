package com.system.customer_support_ticketing_system.mappers;

import com.system.customer_support_ticketing_system.dtos.AuditLogResponse;
import com.system.customer_support_ticketing_system.entities.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(target = "userId",source = "user.id")
    AuditLogResponse toAuditLogResponse(AuditLog auditLog);
}
