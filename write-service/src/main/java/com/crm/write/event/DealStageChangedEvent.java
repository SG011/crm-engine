package com.crm.write.event;
import com.crm.write.domain.DealStage;
import java.time.Instant;
public record DealStageChangedEvent(
    String entityId, String tenantId, String dealId,
    DealStage newStage, Instant occurredAt
) implements DomainEvent {}
