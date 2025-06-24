package com.system.customer_support_ticketing_system.mappers;

import com.system.customer_support_ticketing_system.dtos.ReplyRequest;
import com.system.customer_support_ticketing_system.dtos.ReplyResponse;
import com.system.customer_support_ticketing_system.entities.TicketReply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketReplyMapper {

    TicketReply toEntity(ReplyRequest request);

    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "ticket.id",target = "ticketId")
    ReplyResponse toResponse(TicketReply ticketReply);


}
