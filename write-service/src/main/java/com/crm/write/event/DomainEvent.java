package com.crm.write.event;
import java.time.Instant;
public sealed interface DomainEvent
    permits ContactCreatedEvent, DealCreatedEvent, DealStageChangedEvent,
            ActivityLoggedEvent, TaskAssignedEvent {
    String entityId();
    String tenantId();
    Instant occurredAt();
}
