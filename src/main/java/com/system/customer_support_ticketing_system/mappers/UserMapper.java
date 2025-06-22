package com.system.customer_support_ticketing_system.mappers;

import com.system.customer_support_ticketing_system.dtos.CreateUserDto;
import com.system.customer_support_ticketing_system.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserDto request);
}
