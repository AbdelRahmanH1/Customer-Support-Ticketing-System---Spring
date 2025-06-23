package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.mappers.UserMapper;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import com.system.customer_support_ticketing_system.services.UserService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(){
        var userId = SecurityUtil.getUserId();
        var userDto = userService.getCurrentUser(userId);
        return ResponseUtil.success("Fetched Data successfully",userDto);
    }
}
