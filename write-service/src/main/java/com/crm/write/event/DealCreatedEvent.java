package com.crm.write.event;
import com.crm.write.domain.DealStage;
import java.math.BigDecimal;
import java.time.Instant;
public record DealCreatedEvent(
    String entityId, String tenantId, String title,
    BigDecimal value, DealStage stage, String ownerId, Instant occurredAt
) implements DomainEvent {}
