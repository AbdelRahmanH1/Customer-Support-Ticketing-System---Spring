package com.system.customer_support_ticketing_system.services;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RefreshTokenRedisService {
    private final RedisTemplate<String,Object> redisTemplate;
    private static final String PREFIX = "refresh_token:";
    private static final Duration EXPIRATION = Duration.ofDays(7);

    private String buildKey(String token){
        return PREFIX + token;
    }

    public void setRefreshToken(String token,Long userId){
        Map<String,Object> data = new HashMap<>();
        data.put("userId",userId);
        data.put("isValid",true);

        String key = buildKey(token);
        redisTemplate.opsForHash().putAll(key, data);
        redisTemplate.expire(key, EXPIRATION);
    }
    public Map<Object,Object> getRefreshTokenData(String token){
        return redisTemplate.opsForHash().entries(buildKey(token));
    }
    public void invalidRefreshToken(String token){
        redisTemplate.opsForHash().put(buildKey(token),"isValid",false);
    }
    public void deleteRefreshToken(String token){
        redisTemplate.opsForHash().delete(buildKey(token));
    }
}
