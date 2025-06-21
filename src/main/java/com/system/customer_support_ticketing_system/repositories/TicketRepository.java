package com.system.customer_support_ticketing_system.repositories;

import com.system.customer_support_ticketing_system.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
