package com.crm.write.idempotency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdempotencyServiceTest {

    @Mock StringRedisTemplate redis;
    @Mock ValueOperations<String, String> valueOps;
    @InjectMocks IdempotencyService service;

    @Test
    void firstCall_notDuplicate() {
        when(redis.opsForValue()).thenReturn(valueOps);
        when(valueOps.setIfAbsent(eq("idempotency:key-unique-1"), eq("1"), any()))
            .thenReturn(true); // was absent → first time
        assertThat(service.isDuplicate("key-unique-1")).isFalse();
    }

    @Test
    void secondCall_sameKey_isDuplicate() {
        when(redis.opsForValue()).thenReturn(valueOps);
        when(valueOps.setIfAbsent(eq("idempotency:key-unique-2"), eq("1"), any()))
            .thenReturn(false); // already present → duplicate
        assertThat(service.isDuplicate("key-unique-2")).isTrue();
    }
}
