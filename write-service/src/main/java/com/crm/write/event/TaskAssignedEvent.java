package com.crm.write.event;
import java.time.Instant;
public record TaskAssignedEvent(
    String entityId, String tenantId, String assigneeId,
    String title, Instant dueAt, Instant occurredAt
) implements DomainEvent {}
