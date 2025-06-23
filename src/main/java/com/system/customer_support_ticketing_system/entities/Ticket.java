package com.system.customer_support_ticketing_system.entities;

import com.system.customer_support_ticketing_system.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_responder_id")
    private User adminResponder;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",insertable = false,updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public void changeStatus(TicketStatus newStatus,long adminId) {
        if(this.status == TicketStatus.CLOSED) {
            throw new AccessDeniedException("Ticket has been closed");
        }
        if(this.status == newStatus) {
            return;
        }
        this.setStatus(newStatus);
        if(newStatus == TicketStatus.CLOSED) {
            this.setEndTime(LocalDateTime.now());
        }
        var admin = new User();
        admin.setId(adminId);
        this.adminResponder = admin;
    }
}
