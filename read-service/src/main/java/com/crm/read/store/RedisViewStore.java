package com.crm.read.store;
import com.crm.read.view.ContactView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RedisViewStore {
    private final StringRedisTemplate redis;
    private final ObjectMapper mapper;

    public RedisViewStore(StringRedisTemplate redis, ObjectMapper mapper) {
        this.redis = redis;
        this.mapper = mapper;
    }

    public void saveContact(ContactView view) {
        try {
            String key = view.tenantId() + ":contact:" + view.contactId();
            redis.opsForValue().set(key, mapper.writeValueAsString(view));
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public Optional<ContactView> findContact(String tenantId, String contactId) {
        String key = tenantId + ":contact:" + contactId;
        String json = redis.opsForValue().get(key);
        if (json == null) return Optional.empty();
        try {
            return Optional.of(mapper.readValue(json, ContactView.class));
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
