package com.system.customer_support_ticketing_system.services;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class ResetPasswordRedis {
    private static final String REDIS_CODE_PREFIX = "reset-code:";
    private static final int REDIS_EXPIRE_PER_MIN = 15;
    private final RedisTemplate<String, Object> redisTemplate;

    private String buildKey(String email){
        return REDIS_CODE_PREFIX + email;
    }

    public void setCode(String email, String code){
        redisTemplate.opsForValue().set(buildKey(email), code, Duration.ofMinutes(REDIS_EXPIRE_PER_MIN));
    }
    public String getCode(String email){
        return (String) redisTemplate.opsForValue().get(buildKey(email));
    }
    public void deleteCode(String email){
        redisTemplate.delete(buildKey(email));
    }

}
