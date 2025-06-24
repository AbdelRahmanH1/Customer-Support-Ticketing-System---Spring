package com.system.customer_support_ticketing_system.controllers;

import com.system.customer_support_ticketing_system.dtos.ChangePasswordRequest;
import com.system.customer_support_ticketing_system.services.UserService;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import com.system.customer_support_ticketing_system.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "User Controller",description = "Endpoints for managing User")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation
            (
            summary = "Get current user",
            description = "Fetch the details of the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — missing or invalid token")
    })
    public ResponseEntity<?> getCurrentUser(){
        var userId = SecurityUtil.getUserId();
        var userDto = userService.getCurrentUser(userId);
        return ResponseUtil.success("Fetched Data successfully",userDto);
    }

    @PutMapping()
    @Operation(
            summary = "Change password",
            description = "Allows the current user to update their password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — missing or invalid token")
    })
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        var userId = SecurityUtil.getUserId();
        userService.changePassword(userId,request);
        return ResponseUtil.success("Password Changed successfully",null);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete account",
            description = "Deletes the account of the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — missing or invalid token")
    })
    public ResponseEntity<?> deleteAccount(){
        var userId = SecurityUtil.getUserId();
        userService.deleteUser(userId);
        return ResponseUtil.success("Account Deleted successfully",null);
    }
}
