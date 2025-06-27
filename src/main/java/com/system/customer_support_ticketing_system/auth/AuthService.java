package com.system.customer_support_ticketing_system.auth;

import com.system.customer_support_ticketing_system.config.JwtConfig;
import com.system.customer_support_ticketing_system.dtos.*;
import com.system.customer_support_ticketing_system.enums.UserRole;
import com.system.customer_support_ticketing_system.exceptions.ApiException;
import com.system.customer_support_ticketing_system.exceptions.EmailAlreadyExists;
import com.system.customer_support_ticketing_system.exceptions.InvalidCredentialsException;
import com.system.customer_support_ticketing_system.exceptions.InvalidTokenException;
import com.system.customer_support_ticketing_system.mappers.UserMapper;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import com.system.customer_support_ticketing_system.services.EmailService;
import com.system.customer_support_ticketing_system.utils.CodeGeneratorUtil;
import com.system.customer_support_ticketing_system.utils.EmailTemplateBuilder;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final CodeGeneratorUtil otp;

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
        try {
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
        }catch (AuthenticationException e){
            throw new InvalidCredentialsException();
        }
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

    @Async
    @Transactional
    public void forgetPassword(EmailRequest request) {
        Optional<EmailCodeView> resultOpt = userRepository.findEmailAndCodeByEmail(request.getEmail());
        if (resultOpt.isEmpty()) return;
        EmailCodeView result = resultOpt.get();

        if (result.getPasswordResetCode() != null && !result.getPasswordResetCode().isBlank()) {
            return;
        }

        String code = otp.generate6DigitCode();
        userRepository.updatePasswordResetCode(request.getEmail(), code);

        String html = EmailTemplateBuilder.buildResetCodeTemplate(request.getEmail(), code);
        emailService.send(request.getEmail(), "Password Reset Code", html, true);
    }



    @Transactional
    public void resetPassword(ResetPasswordRequest request){
        String existingCode  = userRepository.findResetCodeByEmail(request.getEmail()).orElse(null);

        if(existingCode== null || !existingCode.equals(request.getCode()))
            throw new ApiException("The email and code must match", HttpStatus.BAD_REQUEST);

        var newPassword = passwordEncoder.encode(request.getNewPassword());

        int updated = userRepository.resetPassword(request.getEmail(), newPassword);
        if(updated == 0) throw new ApiException("Could not update password", HttpStatus.BAD_REQUEST);

    }
}

