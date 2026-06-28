package com.crm.write.handler;
import com.crm.write.command.CreateContactCommand;
import com.crm.write.idempotency.IdempotencyService;
import com.crm.write.outbox.OutboxPublisher;
import com.crm.write.store.EventStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactCommandHandlerTest {
    @Mock IdempotencyService idempotency;
    @Mock EventStore eventStore;
    @Mock OutboxPublisher publisher;
    @InjectMocks ContactCommandHandler handler;

    @Test
    void handle_newCommand_appendsAndPublishes() {
        when(idempotency.isDuplicate(any())).thenReturn(false);
        var cmd = new CreateContactCommand(
            UUID.randomUUID().toString(), "tenant-1", "Alice", "alice@co.com"
        );
        handler.handle(cmd);
        verify(eventStore).append(any());
        verify(publisher).publish(any());
    }

    @Test
    void handle_duplicateCommand_skipsProcessing() {
        when(idempotency.isDuplicate(any())).thenReturn(true);
        var cmd = new CreateContactCommand(
            UUID.randomUUID().toString(), "tenant-1", "Alice", "alice@co.com"
        );
        handler.handle(cmd);
        verifyNoInteractions(eventStore, publisher);
    }
}
