package com.crm.read.projection;
import com.crm.read.store.RedisViewStore;
import com.crm.read.view.ContactView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContactProjection {
    private final RedisViewStore store;
    private final ObjectMapper mapper;

    public ContactProjection(RedisViewStore store, ObjectMapper mapper) {
        this.store = store;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "crm-events", groupId = "crm-read-projection",
                   containerFactory = "kafkaListenerContainerFactory")
    public void onContactCreated(String message) {
        try {
            var node = mapper.readTree(message);
            if (!node.has("email")) return;
            var view = new ContactView(
                node.get("entityId").asText(),
                node.get("tenantId").asText(),
                node.get("name").asText(),
                node.get("email").asText()
            );
            store.saveContact(view);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process event", e);
        }
    }
}
