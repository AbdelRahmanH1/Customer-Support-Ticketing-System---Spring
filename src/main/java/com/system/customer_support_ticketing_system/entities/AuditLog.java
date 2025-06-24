package com.system.customer_support_ticketing_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "action")
    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public AuditLog withAction(String action) {
        this.action = action;
        return this;
    }

    public AuditLog withUserId(Long userId) {
        var user = new User();
        user.setId(userId);
        this.user = user;
        return this;
    }

    public AuditLog withMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }
}
