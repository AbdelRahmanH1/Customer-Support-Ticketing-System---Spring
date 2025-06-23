package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.dtos.ChangePasswordRequest;
import com.system.customer_support_ticketing_system.services.UserService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Transactional
    @PutMapping()
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        var userId = SecurityUtil.getUserId();
        userService.changePassword(userId,request);
        return ResponseUtil.success("Password Changed successfully",null);
    }
}
