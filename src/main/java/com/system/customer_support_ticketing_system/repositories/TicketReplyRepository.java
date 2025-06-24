package com.system.customer_support_ticketing_system.repositories;

import com.system.customer_support_ticketing_system.entities.TicketReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketReplyRepository extends JpaRepository<TicketReply,Long> {
    List<TicketReply> findByTicketIdOrderByCreatedAtAsc(Long ticketId);


    @Modifying
    @Query("DELETE FROM TicketReply r WHERE r.ticket.id= :ticketId")
    void deleteByTicketId(@Param("ticketId") Long ticketId);
}
