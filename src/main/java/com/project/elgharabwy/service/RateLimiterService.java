package com.project.elgharabwy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class RateLimiterService {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;
    private static final int MAX_REQUESTS = 3;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(2);

    public boolean isAllowed(String clientId) {
        String key = "rate:limit:" + clientId;
        ValueOperations<String, Integer> ops = redisTemplate.opsForValue();
        Integer resources = ops.get(key);
        if (resources == null) {
            ops.set(key, 1, WINDOW_DURATION);
            return true;
        } else if ( resources < MAX_REQUESTS) {

            ops.set(key, resources+1, WINDOW_DURATION);
                return true;


        }
        else{
            return false;
        }



    }
}
