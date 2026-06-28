package com.crm.write.store;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.Instant;

@Table("events")
public class EventEntity {

    @PrimaryKeyColumn(name = "entity_id", type = PrimaryKeyType.PARTITIONED)
    private String entityId;

    @PrimaryKeyColumn(name = "version", type = PrimaryKeyType.CLUSTERED)
    private long version;

    @Column("tenant_id")
    private String tenantId;

    @Column("event_type")
    private String eventType;

    @Column("payload")
    private String payload;

    @Column("occurred_at")
    private Instant occurredAt;

    public String getEntityId() { return entityId; }
    public void setEntityId(String v) { this.entityId = v; }
    public long getVersion() { return version; }
    public void setVersion(long v) { this.version = v; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String v) { this.tenantId = v; }
    public String getEventType() { return eventType; }
    public void setEventType(String v) { this.eventType = v; }
    public String getPayload() { return payload; }
    public void setPayload(String v) { this.payload = v; }
    public Instant getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Instant v) { this.occurredAt = v; }
}
