package com.system.customer_support_ticketing_system.repositories;

import com.system.customer_support_ticketing_system.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Page<Ticket> findByUserId(Long userId, Pageable pageable);
}
