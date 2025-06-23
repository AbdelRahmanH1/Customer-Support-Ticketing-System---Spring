package com.system.customer_support_ticketing_system.mappers;

import com.system.customer_support_ticketing_system.dtos.CreateTicketRequest;
import com.system.customer_support_ticketing_system.dtos.TicketResponse;
import com.system.customer_support_ticketing_system.entities.Ticket;
import com.system.customer_support_ticketing_system.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "createdAt", target = "createdDate")
    @Mapping(source = "user",target = "userId" )
    TicketResponse toDto(Ticket ticket);

    Ticket toEntity(CreateTicketRequest request);

    default Long map(User user) {
        return user != null ? user.getId() : null;
    }
}
