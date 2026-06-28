package com.crm.write.store;

import com.crm.write.event.DomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventStore {

    private final EventRepository repo;
    private final ObjectMapper mapper;

    public EventStore(EventRepository repo, ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public void append(DomainEvent event) {
        var existing = repo.findByEntityIdOrderByVersionAsc(event.entityId());
        long nextVersion = existing.size() + 1L;
        var entity = new EventEntity();
        entity.setEntityId(event.entityId());
        entity.setVersion(nextVersion);
        entity.setTenantId(event.tenantId());
        entity.setEventType(event.getClass().getSimpleName());
        entity.setOccurredAt(event.occurredAt());
        try {
            entity.setPayload(mapper.writeValueAsString(event));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
        repo.save(entity);
    }

    public List<EventEntity> load(String entityId) {
        return repo.findByEntityIdOrderByVersionAsc(entityId);
    }
}
