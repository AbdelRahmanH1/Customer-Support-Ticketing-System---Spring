package com.system.customer_support_ticketing_system.services;

import com.system.customer_support_ticketing_system.dtos.ChangePasswordRequest;
import com.system.customer_support_ticketing_system.dtos.UserResponse;
import com.system.customer_support_ticketing_system.mappers.UserMapper;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getCurrentUser(Long userId){
        var user = userRepository.findById(userId).orElseThrow(
                ()->new UsernameNotFoundException("User not found")
        );
        return userMapper.toDto(user);
    }
    public void changePassword(Long userId, ChangePasswordRequest request){
        var newPassword = passwordEncoder.encode(request.getPassword());
        userRepository.updatePassword(userId,newPassword);
    }
}
