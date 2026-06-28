package com.crm.write.idempotency;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class IdempotencyService {
    private static final Duration TTL = Duration.ofHours(24);
    private final StringRedisTemplate redis;

    public IdempotencyService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public boolean isDuplicate(String idempotencyKey) {
        Boolean wasAbsent = redis.opsForValue()
            .setIfAbsent("idempotency:" + idempotencyKey, "1", TTL);
        return Boolean.FALSE.equals(wasAbsent);
    }
}
