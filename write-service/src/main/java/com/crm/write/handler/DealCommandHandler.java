package com.crm.write.handler;
import com.crm.write.command.UpdateDealStageCommand;
import com.crm.write.event.DealStageChangedEvent;
import com.crm.write.idempotency.IdempotencyService;
import com.crm.write.outbox.OutboxPublisher;
import com.crm.write.store.EventStore;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class DealCommandHandler {
    private final IdempotencyService idempotency;
    private final EventStore eventStore;
    private final OutboxPublisher publisher;

    public DealCommandHandler(IdempotencyService idempotency, EventStore eventStore, OutboxPublisher publisher) {
        this.idempotency = idempotency;
        this.eventStore = eventStore;
        this.publisher = publisher;
    }

    public void handle(UpdateDealStageCommand cmd) {
        if (idempotency.isDuplicate(cmd.idempotencyKey())) return;
        var event = new DealStageChangedEvent(
            UUID.randomUUID().toString(), cmd.tenantId(),
            cmd.dealId(), cmd.newStage(), Instant.now()
        );
        eventStore.append(event);
        publisher.publish(event);
    }
}
