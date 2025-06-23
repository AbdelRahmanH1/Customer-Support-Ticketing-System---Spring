package com.system.customer_support_ticketing_system.mappers;

import com.system.customer_support_ticketing_system.dtos.CreateTicketRequest;
import com.system.customer_support_ticketing_system.dtos.TicketResponse;
import com.system.customer_support_ticketing_system.dtos.UpdateTicketRequest;
import com.system.customer_support_ticketing_system.entities.Ticket;
import com.system.customer_support_ticketing_system.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "createdAt", target = "createdDate")
    @Mapping(source = "user",target = "userId" )
    TicketResponse toDto(Ticket ticket);

    Ticket toEntity(CreateTicketRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTicketFromRequest(UpdateTicketRequest request, @MappingTarget Ticket ticket);


    default Long map(User user) {
        return user != null ? user.getId() : null;
    }
}
