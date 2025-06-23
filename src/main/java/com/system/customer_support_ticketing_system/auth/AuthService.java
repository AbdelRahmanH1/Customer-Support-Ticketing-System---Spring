package com.system.customer_support_ticketing_system.auth;

import com.system.customer_support_ticketing_system.config.JwtConfig;
import com.system.customer_support_ticketing_system.dtos.JwtResponse;
import com.system.customer_support_ticketing_system.dtos.LoginRequest;
import com.system.customer_support_ticketing_system.dtos.UserRequest;
import com.system.customer_support_ticketing_system.dtos.UserResponse;
import com.system.customer_support_ticketing_system.enums.UserRole;
import com.system.customer_support_ticketing_system.exceptions.EmailAlreadyExists;
import com.system.customer_support_ticketing_system.exceptions.InvalidTokenException;
import com.system.customer_support_ticketing_system.mappers.UserMapper;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    public UserResponse createUser(UserRequest request){
        if(userRepository.existsUserByEmail(request.getEmail())){
            throw new EmailAlreadyExists();
        }
        var user =  userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
    public JwtResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken",refreshToken);
        cookie.setPath("/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        return new JwtResponse(accessToken);
    }
    public JwtResponse refreshToken(String refreshToken){
        if(!jwtService.validateToken(refreshToken)){
            throw new InvalidTokenException();
        }
        var userId = jwtService.getUserIdFromToken(refreshToken);
        var user = userRepository.findById(userId).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }
}

