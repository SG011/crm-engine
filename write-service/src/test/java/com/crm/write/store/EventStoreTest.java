package com.crm.write.store;

import com.crm.write.event.ContactCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventStoreTest {

    @Mock
    EventRepository repo;

    EventStore eventStore;

    @BeforeEach
    void setUp() {
        eventStore = new EventStore(repo, new ObjectMapper().findAndRegisterModules());
    }

    @Test
    void append_setsVersionToOneForFirstEvent() {
        when(repo.findByEntityIdOrderByVersionAsc(any())).thenReturn(List.of());
        var event = new ContactCreatedEvent(UUID.randomUUID().toString(), "t1", "Alice", "a@b.com", Instant.now());

        eventStore.append(event);

        var captor = ArgumentCaptor.forClass(EventEntity.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getVersion()).isEqualTo(1L);
    }

    @Test
    void append_incrementsVersionForSubsequentEvents() {
        var entityId = UUID.randomUUID().toString();
        var existing = new EventEntity();
        existing.setEntityId(entityId);
        existing.setVersion(1L);
        when(repo.findByEntityIdOrderByVersionAsc(entityId)).thenReturn(List.of(existing));

        var event = new ContactCreatedEvent(entityId, "t1", "Alice", "a@b.com", Instant.now());
        eventStore.append(event);

        var captor = ArgumentCaptor.forClass(EventEntity.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getVersion()).isEqualTo(2L);
    }

    @Test
    void append_setsEventTypeToSimpleClassName() {
        when(repo.findByEntityIdOrderByVersionAsc(any())).thenReturn(List.of());
        var event = new ContactCreatedEvent(UUID.randomUUID().toString(), "t1", "Alice", "a@b.com", Instant.now());

        eventStore.append(event);

        var captor = ArgumentCaptor.forClass(EventEntity.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getEventType()).isEqualTo("ContactCreatedEvent");
    }

    @Test
    void append_setsTenantId() {
        when(repo.findByEntityIdOrderByVersionAsc(any())).thenReturn(List.of());
        var event = new ContactCreatedEvent(UUID.randomUUID().toString(), "tenant-xyz", "Alice", "a@b.com", Instant.now());

        eventStore.append(event);

        var captor = ArgumentCaptor.forClass(EventEntity.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo("tenant-xyz");
    }

    @Test
    void load_delegatesToRepository() {
        String entityId = UUID.randomUUID().toString();
        when(repo.findByEntityIdOrderByVersionAsc(entityId)).thenReturn(List.of());

        var result = eventStore.load(entityId);

        assertThat(result).isEmpty();
        verify(repo).findByEntityIdOrderByVersionAsc(entityId);
    }
}
