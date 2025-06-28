package com.system.customer_support_ticketing_system.config;

import com.system.customer_support_ticketing_system.auth.JwtService;
import com.system.customer_support_ticketing_system.entities.User;
import com.system.customer_support_ticketing_system.enums.UserRole;
import com.system.customer_support_ticketing_system.repositories.UserRepository;
import com.system.customer_support_ticketing_system.services.EmailService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppWarmUp {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisConnectionFactory redisConnectionFactory;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @PostConstruct
    public void warmUp() {
        log.info("🔁 Starting application warm-up...");

        try {
            warmUpRedis();
            warmUpJwt();
            warmUpDatabase();
            warmUpEmail();

            log.info("✅ Application warm-up completed successfully.");
        } catch (Exception e) {
            log.error("❌ Application warm-up failed: {}", e.getMessage(), e);
        }
    }
    @PostConstruct
    public void benchmarkRedis() {
        long start = System.nanoTime();
        redisTemplate.opsForValue().get("user:123");
        long end = System.nanoTime();
        log.info("Redis call took {} ms", (end - start) / 1_000_000);
    }
    private void warmUpRedis() {
        redisConnectionFactory.getConnection().ping();
        redisTemplate.opsForValue().set("warmup-key", "1", Duration.ofMinutes(1));
        redisTemplate.opsForValue().get("warmup-key");
        log.debug("✅ Redis warm-up successful.");
    }

    private void warmUpJwt() {
        User mockUser = new User();
        mockUser.setId(0L);
        mockUser.setRole(UserRole.USER);
        mockUser.setPassword("password");

        String token = jwtService.generateAccessToken(mockUser);
        jwtService.validateToken(token);
        log.debug("✅ JWT warm-up successful.");
    }

    private void warmUpDatabase() {
        long userCount = userRepository.count();
        log.debug("✅ Database warm-up successful. Users count: {}", userCount);
    }

    private void warmUpEmail() {
        emailService.send(
                "no-reply@localhost",
                "Warm-up Email",
                "<p>Warming up mail sender.</p>",
                true
        );
        log.debug("✅ Email service warm-up successful.");
    }
}
