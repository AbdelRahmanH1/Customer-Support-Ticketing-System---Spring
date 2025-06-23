package com.system.customer_support_ticketing_system.mappers;

import com.system.customer_support_ticketing_system.dtos.UserRequest;
import com.system.customer_support_ticketing_system.dtos.UserResponse;
import com.system.customer_support_ticketing_system.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(User user);
    User toEntity(UserRequest request);
}
