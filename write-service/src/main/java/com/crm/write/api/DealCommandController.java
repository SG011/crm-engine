package com.crm.write.api;
import com.crm.write.command.UpdateDealStageCommand;
import com.crm.write.handler.DealCommandHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deals")
public class DealCommandController {
    private final DealCommandHandler handler;
    public DealCommandController(DealCommandHandler handler) { this.handler = handler; }

    @PatchMapping("/{dealId}/stage")
    public ResponseEntity<Void> updateStage(@PathVariable("dealId") String dealId, @RequestBody UpdateDealStageCommand cmd) {
        handler.handle(cmd);
        return ResponseEntity.accepted().build();
    }
}
