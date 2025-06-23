package com.system.customer_support_ticketing_system.auth;

import com.system.customer_support_ticketing_system.dtos.LoginRequest;
import com.system.customer_support_ticketing_system.dtos.UserRequest;
import com.system.customer_support_ticketing_system.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest request)  {

        var userDto = authService.createUser(request);
        return ResponseUtil.success("User registered successfully",userDto , HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request)  {

        var accessToken = authService.login(request);
        return ResponseUtil.success("Login success",accessToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value ="refreshToken")String refreshToken)  {
        var accessToken = authService.refreshToken(refreshToken);
        return ResponseUtil.success("Refresh success",accessToken);
    }

}
