package com.crm.write.handler;
import com.crm.write.command.CreateContactCommand;
import com.crm.write.event.ContactCreatedEvent;
import com.crm.write.idempotency.IdempotencyService;
import com.crm.write.outbox.OutboxPublisher;
import com.crm.write.store.EventStore;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class ContactCommandHandler {
    private final IdempotencyService idempotency;
    private final EventStore eventStore;
    private final OutboxPublisher publisher;

    public ContactCommandHandler(IdempotencyService idempotency, EventStore eventStore, OutboxPublisher publisher) {
        this.idempotency = idempotency;
        this.eventStore = eventStore;
        this.publisher = publisher;
    }

    public void handle(CreateContactCommand cmd) {
        if (idempotency.isDuplicate(cmd.idempotencyKey())) return;
        var event = new ContactCreatedEvent(
            UUID.randomUUID().toString(), cmd.tenantId(),
            cmd.name(), cmd.email(), Instant.now()
        );
        eventStore.append(event);
        publisher.publish(event);
    }
}
