package com.crm.write.outbox;

import com.crm.write.event.DomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OutboxPublisher {
    private static final String TOPIC = "crm-events";
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;

    public OutboxPublisher(KafkaTemplate<String, String> kafka, ObjectMapper mapper) {
        this.kafka = kafka;
        this.mapper = mapper;
    }

    public void publish(DomainEvent event) {
        try {
            String payload = mapper.writeValueAsString(event);
            kafka.send(TOPIC, event.tenantId(), payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish event", e);
        }
    }
}
