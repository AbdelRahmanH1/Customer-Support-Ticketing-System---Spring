package com.system.customer_support_ticketing_system.auth;

import com.system.customer_support_ticketing_system.config.JwtConfig;
import com.system.customer_support_ticketing_system.dtos.*;
import com.system.customer_support_ticketing_system.entities.User;
import com.system.customer_support_ticketing_system.enums.UserRole;
import com.system.customer_support_ticketing_system.exceptions.ApiException;
import com.system.customer_support_ticketing_system.exceptions.EmailAlreadyExists;
import com.system.customer_support_ticketing_system.exceptions.InvalidCredentialsException;
import com.system.customer_support_ticketing_system.exceptions.InvalidTokenException;
import com.system.customer_support_ticketing_system.mappers.UserMapper;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import com.system.customer_support_ticketing_system.services.EmailService;
import com.system.customer_support_ticketing_system.services.RefreshTokenRedisService;
import com.system.customer_support_ticketing_system.services.ResetPasswordRedis;
import com.system.customer_support_ticketing_system.utils.CodeGeneratorUtil;
import com.system.customer_support_ticketing_system.utils.EmailTemplateBuilder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
    private final ResetPasswordRedis resetPasswordRedis;
    private final RefreshTokenRedisService refreshTokenRedis;

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
    public JwtResponse login(LoginRequest request, HttpServletResponse response){
        try {
           var authentication =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            var user =  (User) authentication.getPrincipal();
            var accessToken = jwtService.generateAccessToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            refreshTokenRedis.setRefreshToken(refreshToken,user.getId());

            var cookie = new Cookie("refreshToken",refreshToken);
            cookie.setPath("/auth/refresh");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
            response.addCookie(cookie);
            return new JwtResponse(accessToken);
        }catch (AuthenticationException e){
            throw new InvalidCredentialsException();
        }
    }
    public JwtResponse refreshToken(String refreshToken) {
        if (!jwtService.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        var redisData = refreshTokenRedis.getRefreshTokenData(refreshToken);
        if (redisData == null || redisData.isEmpty()) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        Boolean isValid = (Boolean) redisData.get("isValid");
        if (isValid == null || !isValid) {
            throw new InvalidTokenException("Refresh token has been invalidated.");
        }
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidTokenException("User not found."));

        if (user.isDeleted()) {
            throw new InvalidTokenException("User account is deactivated.");
        }

        String accessToken = jwtService.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }



    public void forgetPassword(EmailRequest request) {
        var email = request.getEmail();
        if(!userRepository.existsUserByEmail(email)){
            return;
        }
        if(resetPasswordRedis.getCode(email)!=null){
            return;
        }
        String code = otp.generate6DigitCode();
        resetPasswordRedis.setCode(email, code);

        String html = EmailTemplateBuilder.buildResetCodeTemplate(request.getEmail(), code);
        emailService.send(request.getEmail(), "Password Reset Code", html, true);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request){
        String email = request.getEmail();
        String inputCode = request.getCode();

        String existingCode = resetPasswordRedis.getCode(email);

        if(existingCode == null || !existingCode.equals(inputCode)){
            throw new ApiException("The email and code must match",HttpStatus.BAD_REQUEST);
        }
        String newPassword = passwordEncoder.encode(request.getNewPassword());
        int updated = userRepository.resetPassword(email, newPassword);
        if(updated == 0){
            throw new ApiException("Could not update password",HttpStatus.BAD_REQUEST);
        }
        resetPasswordRedis.deleteCode(email);
    }
}

