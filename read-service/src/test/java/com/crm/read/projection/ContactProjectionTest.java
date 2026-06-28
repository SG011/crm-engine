package com.crm.read.projection;
import com.crm.read.store.RedisViewStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContactProjectionTest {
    @Mock RedisViewStore store;
    @Spy ObjectMapper mapper = new ObjectMapper();
    @InjectMocks ContactProjection projection;

    @Test
    void onContactCreated_savesViewToRedis() {
        String json = """
            {"entityId":"id-1","tenantId":"t1","name":"Alice","email":"alice@co.com","occurredAt":"2026-01-01T00:00:00Z"}
        """;
        projection.onContactCreated(json);
        verify(store).saveContact(any());
    }
}
