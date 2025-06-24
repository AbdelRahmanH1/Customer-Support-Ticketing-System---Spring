package com.system.customer_support_ticketing_system.repositories;

import com.system.customer_support_ticketing_system.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
