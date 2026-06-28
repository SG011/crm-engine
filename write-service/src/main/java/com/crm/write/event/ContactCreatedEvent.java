package com.crm.write.event;
import java.time.Instant;
public record ContactCreatedEvent(
    String entityId, String tenantId, String name, String email, Instant occurredAt
) implements DomainEvent {}
