package com.system.customer_support_ticketing_system.services;

import com.system.customer_support_ticketing_system.dtos.CreateTicketRequest;
import com.system.customer_support_ticketing_system.dtos.TicketResponse;
import com.system.customer_support_ticketing_system.entities.Ticket;
import com.system.customer_support_ticketing_system.entities.User;
import com.system.customer_support_ticketing_system.enums.TicketStatus;
import com.system.customer_support_ticketing_system.exceptions.TicketNotFoundException;
import com.system.customer_support_ticketing_system.mappers.TicketMapper;
import com.system.customer_support_ticketing_system.repositories.TicketRepository;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;

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

    public Page<TicketResponse> updateTicket(Long userID, Long userId, int page, String userRole){
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

        Page<Ticket> ticketPage;

        if ("ADMIN".equals(userRole)) {
            if (userId != null) {
                if (!userRepository.existsById(userId)) {
                    throw new UsernameNotFoundException("User not found");
                }
                ticketPage = ticketRepository.findByUserId(userId, pageable);
            } else {
                ticketPage = ticketRepository.findAll(pageable);
            }
        } else {
            ticketPage = ticketRepository.findByUserId(userID, pageable);
        }

        return ticketPage.map(ticketMapper::toDto);

    }

    public TicketResponse getTicketById(Long userId, String userRole,Long ticketId){
        var optionalTicket = ticketRepository.findById(ticketId);
        if (optionalTicket.isEmpty()) {
            throw new TicketNotFoundException();
        }

        var ticket = optionalTicket.get();

        if ("USER".equals(userRole) && !ticket.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }
        return  ticketMapper.toDto(ticket);
    }
}
