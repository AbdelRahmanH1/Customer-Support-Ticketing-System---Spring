package com.system.customer_support_ticketing_system.services;

import com.system.customer_support_ticketing_system.dtos.CreateTicketRequest;
import com.system.customer_support_ticketing_system.dtos.TicketResponse;
import com.system.customer_support_ticketing_system.entities.User;
import com.system.customer_support_ticketing_system.enums.TicketStatus;
import com.system.customer_support_ticketing_system.mappers.TicketMapper;
import com.system.customer_support_ticketing_system.repositories.TicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final EntityManager entityManager;

    @Transactional
    public TicketResponse createTicket(Long userId,CreateTicketRequest request){
        var user = new User();
        user.setId(userId);

        var ticket = ticketMapper.toEntity(request);
        ticket.setUser(user);
        ticket.setStatus(TicketStatus.OPEN);

        ticketRepository.save(ticket);
        entityManager.refresh(ticket);

        return ticketMapper.toDto(ticket);
    }
}
