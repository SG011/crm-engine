package com.crm.read.api;

import com.crm.read.store.RedisViewStore;
import com.crm.read.view.ContactView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
public class ContactQueryController {
    private final RedisViewStore store;

    public ContactQueryController(RedisViewStore store) {
        this.store = store;
    }

    @GetMapping("/{tenantId}/{contactId}")
    public ResponseEntity<ContactView> getContact(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("contactId") String contactId) {
        return store.findContact(tenantId, contactId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
