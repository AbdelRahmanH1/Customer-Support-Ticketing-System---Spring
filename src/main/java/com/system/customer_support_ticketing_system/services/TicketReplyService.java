package com.system.customer_support_ticketing_system.services;

import com.system.customer_support_ticketing_system.dtos.ReplyRequest;
import com.system.customer_support_ticketing_system.dtos.ReplyResponse;
import com.system.customer_support_ticketing_system.dtos.TicketReplyResponse;
import com.system.customer_support_ticketing_system.entities.Ticket;
import com.system.customer_support_ticketing_system.entities.TicketReply;
import com.system.customer_support_ticketing_system.entities.User;
import com.system.customer_support_ticketing_system.exceptions.ApiException;
import com.system.customer_support_ticketing_system.exceptions.TicketNotFoundException;
import com.system.customer_support_ticketing_system.mappers.TicketReplyMapper;
import com.system.customer_support_ticketing_system.repositories.TicketReplyRepository;
import com.system.customer_support_ticketing_system.repositories.TicketRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketReplyService {

    private final TicketRepository ticketRepository;
    private final TicketReplyRepository ticketReplyRepository;
    private final TicketReplyMapper ticketReplyMapper;
    private final EntityManager entityManager;

    @Transactional
    public ReplyResponse addReply(Long userId, boolean isAdmin, Long ticketId, ReplyRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        if (ticket.isClosed()) {
            throw new ApiException("Ticket is closed. You can't reply.", HttpStatus.BAD_REQUEST);
        }

        List<TicketReply> replies = ticketReplyRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);

        if (replies.isEmpty() && !isAdmin) {
            throw new ApiException("Only admin can send the first reply.", HttpStatus.BAD_REQUEST);
        }
        if (!replies.isEmpty()) {
            Long lastReplierId = replies.get(replies.size() - 1).getUser().getId();
            if (lastReplierId.equals(userId)) {
                throw new ApiException("Please wait for a response before sending another reply.", HttpStatus.BAD_REQUEST);
            }
        }

        TicketReply ticketReply = ticketReplyMapper.toEntity(request);
        ticketReply.setTicket(ticket);
        User user = new User();
        user.setId(userId);
        ticketReply.setUser(user);

        ticketReplyRepository.save(ticketReply);
        entityManager.refresh(ticketReply);

        return ticketReplyMapper.toResponse(ticketReply);
    }

    public List<TicketReplyResponse> getReply(Long userId, boolean isAdmin, Long ticketId) {
        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        if (!isAdmin && !ticket.isOwnedBy(userId)) {
            throw new ApiException("You are not allowed to view replies for this ticket.", HttpStatus.FORBIDDEN);
        }

        var replies = ticketReplyRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
        return replies.stream()
                .map(ticketReplyMapper::toDto)
                .toList();
    }
}
