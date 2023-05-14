package com.uniwa.contract.account.service;

import com.uniwa.contract.account.model.AccountDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(AccountDetails accountDetails) {

        redisTemplate.opsForValue().set(accountDetails.getEmail(), accountDetails, Duration.ofHours(2));

    }

    public AccountDetails fetch(String username) {

       return (AccountDetails) redisTemplate.opsForValue().get(username);
    }
}
