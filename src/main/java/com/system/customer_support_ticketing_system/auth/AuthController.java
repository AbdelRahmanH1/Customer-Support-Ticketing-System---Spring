package com.system.customer_support_ticketing_system.auth;

import com.system.customer_support_ticketing_system.dtos.EmailRequest;
import com.system.customer_support_ticketing_system.dtos.LoginRequest;
import com.system.customer_support_ticketing_system.dtos.ResetPasswordRequest;
import com.system.customer_support_ticketing_system.dtos.UserRequest;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Auth Controller", description = "Endpoints for managing auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided email, username, and password"
    )
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest request) {

        var userDto = authService.createUser(request);
        return ResponseUtil.success("User registered successfully", userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT access token"
    )
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {

        var accessToken = authService.login(request,response);
        return ResponseUtil.success("Login success", accessToken);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using the refresh token from the cookie"
    )
    public ResponseEntity<?> refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        var accessToken = authService.refreshToken(refreshToken);
        return ResponseUtil.success("Refresh success", accessToken);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody EmailRequest request) {
        authService.forgetPassword(request);
        return ResponseUtil.success("If the email exists, a reset code has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseUtil.success("Password reset successfully",null);

    }
}
