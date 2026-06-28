package com.crm.write.api;
import com.crm.write.command.CreateContactCommand;
import com.crm.write.handler.ContactCommandHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
public class ContactCommandController {
    private final ContactCommandHandler handler;
    public ContactCommandController(ContactCommandHandler handler) { this.handler = handler; }

    @PostMapping
    public ResponseEntity<Void> createContact(@RequestBody CreateContactCommand cmd) {
        handler.handle(cmd);
        return ResponseEntity.accepted().build();
    }
}
