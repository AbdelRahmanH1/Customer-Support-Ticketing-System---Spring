package com.system.customer_support_ticketing_system.repositories;

import com.system.customer_support_ticketing_system.entities.TicketReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketReplyRepository extends JpaRepository<TicketReply,Long> {
    List<TicketReply> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}
