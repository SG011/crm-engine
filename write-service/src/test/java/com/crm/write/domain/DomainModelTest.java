package com.crm.write.domain;

import com.crm.write.event.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class DomainModelTest {

    @Test
    void contactCreatedEvent_carriesTenantId() {
        var event = new ContactCreatedEvent(
            UUID.randomUUID().toString(),
            "tenant-abc",
            "John Doe",
            "john@acme.com",
            Instant.now()
        );
        assertThat(event.tenantId()).isEqualTo("tenant-abc");
        assertThat(event.email()).isEqualTo("john@acme.com");
    }

    @Test
    void dealStageChangedEvent_hasCorrectStage() {
        var event = new DealStageChangedEvent(
            UUID.randomUUID().toString(),
            "tenant-abc",
            UUID.randomUUID().toString(),
            DealStage.PROPOSAL,
            Instant.now()
        );
        assertThat(event.newStage()).isEqualTo(DealStage.PROPOSAL);
    }

    @Test
    void domainEvent_isSealed() {
        DomainEvent event = new ContactCreatedEvent(
            UUID.randomUUID().toString(), "t1", "Name", "e@e.com", Instant.now()
        );
        assertThat(event).isInstanceOf(ContactCreatedEvent.class);
    }
}
