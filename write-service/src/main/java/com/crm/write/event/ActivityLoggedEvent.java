package com.crm.write.event;
import java.time.Instant;
public record ActivityLoggedEvent(
    String entityId, String tenantId, String contactId,
    String activityType, String note, Instant occurredAt
) implements DomainEvent {}
