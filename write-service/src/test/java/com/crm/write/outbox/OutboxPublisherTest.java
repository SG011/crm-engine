package com.crm.write.outbox;

import com.crm.write.event.ContactCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.Instant;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OutboxPublisherTest {
    @Mock KafkaTemplate<String, String> kafka;
    @Spy ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @InjectMocks OutboxPublisher publisher;

    @Test
    void publish_sendsToCorrectTopicWithTenantIdAsKey() throws Exception {
        var event = new ContactCreatedEvent(
            UUID.randomUUID().toString(), "tenant-xyz",
            "Bob", "bob@test.com", Instant.now()
        );
        publisher.publish(event);
        verify(kafka).send(eq("crm-events"), eq("tenant-xyz"), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void publish_payloadContainsEmail() throws Exception {
        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        var event = new ContactCreatedEvent(
            UUID.randomUUID().toString(), "tenant-abc",
            "Alice", "alice@co.com", Instant.now()
        );
        publisher.publish(event);
        verify(kafka).send(eq("crm-events"), eq("tenant-abc"), payloadCaptor.capture());
        assertThat(payloadCaptor.getValue()).contains("alice@co.com");
    }
}
