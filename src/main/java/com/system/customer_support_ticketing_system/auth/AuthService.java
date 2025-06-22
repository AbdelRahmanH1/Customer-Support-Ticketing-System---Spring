package com.system.customer_support_ticketing_system.auth;

import com.system.customer_support_ticketing_system.dtos.CreateUserDto;
import com.system.customer_support_ticketing_system.dtos.UserResponse;
import com.system.customer_support_ticketing_system.enums.UserRole;
import com.system.customer_support_ticketing_system.exceptions.EmailAlreadyExists;
import com.system.customer_support_ticketing_system.mappers.UserMapper;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(@Valid @RequestBody CreateUserDto request){
        if(userRepository.existsUserByEmail(request.getEmail())){
            throw new EmailAlreadyExists();
        }
        var user =  userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}

